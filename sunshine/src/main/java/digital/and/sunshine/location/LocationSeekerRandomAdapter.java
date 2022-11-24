package digital.and.sunshine.location;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "location-seeker", name = "random.enabled", havingValue = "true")
public class LocationSeekerRandomAdapter implements LocationSeekerService {

  private static final int EARTH_RADIUS = 6371; // km
  private static final double DEG_TO_RAD = Math.PI / 180.0;
  private static final double TWO_PI = Math.PI * 2;
  private static final double DEGREE = EARTH_RADIUS * TWO_PI / 360 * 1000; // 1° latitude in meters

  @Override
  public Places getNearPlaces(final RetrieveNearPlaces retrieveNearPlaces) {

    final List<Coordinates> coordinates = this.generateLocation(
        retrieveNearPlaces.coordinates(),
        retrieveNearPlaces.radius(),
        retrieveNearPlaces.numberOfPlaces()
    );

    return new Places(coordinates);
  }

  private List<Coordinates> generateLocation(final Coordinates coordinates, final int radius, final int numberOfCoordinates) {
    return IntStream.range(0, numberOfCoordinates)
        .mapToObj(value -> this.generateCoordinates(coordinates, radius))
        .toList();

  }

  private Coordinates generateCoordinates(final Coordinates coordinates, final int radius) {
    // random distance within [min-max] in km in a non-uniform way

    final var maxKm = radius * 1000;
    final var minKm = 0;
    final var r = ((maxKm - minKm + 1) * Math.random() * 0.5) + minKm;

    // random angle
    final var theta = Math.random() * 2 * Math.PI;

    final var dy = r * Math.sin(theta);
    final var dx = r * Math.cos(theta);

    final var newLatitude = new BigDecimal(coordinates.lat() + dy / DEGREE).setScale(6, RoundingMode.HALF_UP).doubleValue();
    final var newLongitude = new BigDecimal(coordinates.lon() + dx / (DEGREE * Math.cos(toRadians(coordinates.lat())))).setScale(6, RoundingMode.HALF_UP).doubleValue();
    return Coordinates.of(newLongitude, newLatitude);
  }

  /**
   * Basically it is the Haversine distance function. Based on: @see <a
   * href="http://www.movable-type.co.uk/scripts/latlong.html">...</a>
   */
  private double distance(final Coordinates source, final Coordinates target) {
    final var dLat = toRadians(target.lat() - source.lat());  // deg2rad below
    final var dLon = toRadians(target.lon() - source.lon());

    final var a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
        + Math.cos(toRadians(source.lat())) * Math.cos(toRadians(target.lat())) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

    final var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c; // Distance in km
  }

  private static double toRadians(final double deg) {
    return deg * DEG_TO_RAD;
  }

}
