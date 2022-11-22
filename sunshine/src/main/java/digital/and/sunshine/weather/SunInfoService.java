package digital.and.sunshine.weather;

import digital.and.sunshine.weather.provider.openuv.OpenUVClient;
import digital.and.sunshine.weather.provider.openuv.model.SunResponse;
import digital.and.sunshine.location.Coordination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SunInfoService {

  private final OpenUVClient openUVClient;

  public SunResponse retrieveByCoordination(final Coordination coordination) {
    return this.openUVClient.retrieveByCoordination(coordination);
  }

}
