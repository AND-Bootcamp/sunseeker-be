package digital.and.sunshine.weather.provider.openweathermap;

import digital.and.sunshine.weather.provider.openweathermap.model.Coordination;
import digital.and.sunshine.weather.provider.openweathermap.model.SolarRadiationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class SolarRadiationClient {

  //TODO: get from config file
  private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/solar_radiation";
  private static final String LAT = "lat";
  private static final String LON = "lon";
  private static final String APPID = "appid";
  private static final String API_KEY = "af5f69c09060f576d20ccaebae598e12";

  private final RestTemplate restTemplate;

  public SolarRadiationResponse retrieveByCoordination(Coordination coordination) {

    UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(BASE_URL)
        .queryParam(LAT, coordination.lat())
        .queryParam(LON, coordination.lon())
        .queryParam(APPID, API_KEY)
        .build();

    log.info("Request Uri {}", uriComponents);

    var response = restTemplate.getForEntity(uriComponents.toUri(), SolarRadiationResponse.class);

    //TODO: check response type, and add resiliency configs

    return response.getBody();
  }
}
