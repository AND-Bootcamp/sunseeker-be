package digital.and.sunshine.location;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "location-seeker", name = "google-map.enabled", havingValue = "true")
public class LocationSeekerGoogleMapAdapter implements LocationSeekerService{

  @Override
  public Places getNearPlaces(RetrieveNearPlaces retrieveNearPlaces) {
    return null;
  }
}
