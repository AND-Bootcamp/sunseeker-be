package digital.and.sunshine.weather.provider.openuv;

import digital.and.sunshine.weather.provider.openuv.model.SunResponse;
import digital.and.sunshine.weather.provider.openweathermap.model.Coordination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenUVClient {

  //TODO: get from config file
  private static final String BASE_URL = "https://api.openuv.io/api/v1/uv";
  private static final String LAT = "lat";
  private static final String LON = "lng";

  private static final String X_ACCESS_TOKEN = "1be225e2951a1654c78683e2c2be6298";

  private final RestTemplate restTemplate;


  public SunResponse retrieveByCoordination(final Coordination coordination) {

    final UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(BASE_URL)
        .queryParam(LAT, coordination.lat())
        .queryParam(LON, coordination.lon())
        .build();

    log.info("Request Uri {}", uriComponents);

    final var response = this.restTemplate.exchange(
        uriComponents.toUri(),
        HttpMethod.GET,
        defaultHttpEntity(),
        SunResponse.class);

    //TODO: check response type, and add resiliency configs

    return response.getBody();
  }

  private static HttpEntity<?> defaultHttpEntity() {
    return new HttpEntity<>(defaultHeaders());
  }

  private static HttpHeaders defaultHeaders() {
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("x-access-token", X_ACCESS_TOKEN);
    return httpHeaders;
  }

}
