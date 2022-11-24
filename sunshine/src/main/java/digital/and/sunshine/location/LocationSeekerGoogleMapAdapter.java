package digital.and.sunshine.location;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-seeker.google-map.enabled", havingValue = "true")
public class LocationSeekerGoogleMapAdapter implements LocationSeekerService {

  @Value("${google-map.api-key}")
  private String apiKey;

  @Override
  public Places getNearPlaces(final RetrieveNearPlaces retrieveNearPlaces) {
    try {
      final GeoApiContext context = new GeoApiContext.Builder()
          .apiKey(this.apiKey)
          .build();

      final LatLng latLng = this.getLatLng(retrieveNearPlaces);

      final PlacesSearchResponse placesSearchResponse = PlacesApi.nearbySearchQuery(context, latLng)
          .radius(retrieveNearPlaces.radiusInMeter())
          .await();

      log.debug("Response {}", List.of(placesSearchResponse.results));

      final List<Coordinates> coordinatesList = Arrays.stream(placesSearchResponse.results)
          .map(this::getCoordinates)
          .toList();

      context.shutdown();

      return new Places(coordinatesList);
    } catch (final ApiException | IOException e) {
      throw new RuntimeException(e);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
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
