package digital.and.sunshine.location;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "location-seeker", name = "google-map.enabled", havingValue = "true")
public class LocationSeekerGoogleMapAdapter implements LocationSeekerService {

  @Override
  public Places getNearPlaces(final RetrieveNearPlaces retrieveNearPlaces) {
    try {
      final GeoApiContext context = new GeoApiContext.Builder()
          .apiKey("a6905bec531bcc6b8cebf3169540bc24")
          .build();

      final LatLng latLng = this.getLatLng(retrieveNearPlaces);

      final PlacesSearchResponse placesSearchResponse = PlacesApi.nearbySearchQuery(context, latLng)
          .radius(retrieveNearPlaces.radiusInMeter())
          .await();

      final List<Coordinates> coordinatesList = Arrays.stream(placesSearchResponse.results)
          .map(this::getCoordinates)
          .toList();

      context.shutdown();

      return new Places(coordinatesList);
    } catch (final Exception e) {
      log.error("Exception has been occurred ", e);
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private LatLng getLatLng(final RetrieveNearPlaces retrieveNearPlaces) {
    final Coordinates coordinates = retrieveNearPlaces.coordinates();

    return new LatLng(coordinates.lat(), coordinates.lon());
  }

  private Coordinates getCoordinates(final PlacesSearchResult placesSearchResult) {
    final LatLng location = placesSearchResult.geometry.location;

    return Coordinates.of(location.lng, location.lat);
  }
}
