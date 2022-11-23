package digital.and.sunshine.weather;

import digital.and.sunshine.location.Coordinates;
import digital.and.sunshine.weather.provider.openuv.OpenUVClient;
import digital.and.sunshine.weather.provider.openuv.model.SunResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SunInfoService {

  private final OpenUVClient openUVClient;

  public SunResponse retrieveByCoordination(final Coordinates coordinates) {
    return this.openUVClient.retrieveByCoordination(coordinates);
  }

}
