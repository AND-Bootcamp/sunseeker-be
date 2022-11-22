package digital.and.sunshine.weather.provider.openuv;

import digital.and.sunshine.location.Coordination;
import digital.and.sunshine.weather.provider.openuv.auth.RequestOptions;
import digital.and.sunshine.weather.provider.openuv.auth.TokenProvider;
import digital.and.sunshine.weather.provider.openuv.model.SunResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenUVClient {

  private static final String LAT = "lat";
  private static final String LON = "lng";
  private static final String X_ACCESS_TOKEN = "x-access-token";

  private final RestTemplate restTemplate;
  private final RequestOptions requestOptions;
  private final TokenProvider tokenProvider;

  @Retry(name = "openuv")
  @Bulkhead(name = "openuv")
  @CircuitBreaker(name = "openuv")
  public SunResponse retrieveByCoordination(final Coordination coordination) {

    final UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(this.requestOptions.getBaseUrl())
        .queryParam(LAT, coordination.lat())
        .queryParam(LON, coordination.lon())
        .build();

    log.info("Request Uri {}", uriComponents);

    final URI uri = uriComponents.toUri();

    try {
      final var response = this.restTemplate.exchange(
          uri,
          HttpMethod.GET,
          this.defaultHttpEntity(),
          SunResponse.class);

      log.info("Response {}", response);

      return response.getBody();

    } catch (final HttpClientErrorException ex) {

      final HttpStatus statusCode = ex.getStatusCode();

      if (isDailyQuotaExceeded(statusCode)) {
        log.warn("Starting retry request for {} with a new token.", uri);

        final var response = this.restTemplate.exchange(uri, HttpMethod.GET,
            this.createHttpEntityWithRefreshedToken(), SunResponse.class);

        return response.getBody();
      }

      throw ex;
    }
  }

  private HttpEntity<?> defaultHttpEntity() {
    return new HttpEntity<>(this.defaultHeaders());
  }

  private HttpEntity<?> createHttpEntityWithRefreshedToken() {
    return new HttpEntity<>(this.refreshAuthToken());
  }

  private HttpHeaders defaultHeaders() {
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(X_ACCESS_TOKEN, this.tokenProvider.current());
    return httpHeaders;
  }

  private HttpHeaders refreshAuthToken() {
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(X_ACCESS_TOKEN, this.tokenProvider.next());
    return httpHeaders;
  }

  //FIXME : use restTemplate exception handler instead of try catch
  private static boolean isDailyQuotaExceeded(final HttpStatus statusCode) {
    return statusCode == HttpStatus.TOO_MANY_REQUESTS || statusCode == HttpStatus.UNAUTHORIZED
        || statusCode == HttpStatus.FORBIDDEN;
  }

}
