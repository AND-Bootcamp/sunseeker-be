package digital.and.sunshine.weather;

import digital.and.sunshine.weather.client.SolarRadiationClient;
import digital.and.sunshine.weather.model.Coordination;
import digital.and.sunshine.weather.response.SolarRadiationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SolarRadiationService {

  private final SolarRadiationClient solarRadiationClient;

  public SolarRadiationResponse retrieveByCoordination(Coordination coordination) {
    return solarRadiationClient.retrieveByCoordination(coordination);
  }

}
