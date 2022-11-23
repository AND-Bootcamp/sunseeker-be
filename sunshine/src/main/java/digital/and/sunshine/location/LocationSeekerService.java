package digital.and.sunshine.location;

import java.util.random.RandomGenerator;
import org.springframework.stereotype.Service;

@Service
public class LocationSeekerService {

  private static final int EARTH_RADIUS = 6371000; // meters
  private static final double DEG_TO_RAD = Math.PI / 180.0;
  private static final double THREE_PI = Math.PI * 3;
  private static final double TWO_PI = Math.PI * 2;

  private static final RandomGenerator random = RandomGenerator.getDefault();

  /**
   * Basically it is the Haversine distance function. Based on: @see <a
   * href="http://www.movable-type.co.uk/scripts/latlong.html">...</a>
   */
  public double distance(final Coordination source, final Coordination target) {

    final var rP1 = Coordination.of(toRadians(source.lon()), toRadians(source.lat()));

    final var rP2 = Coordination.of(toRadians(target.lon()), toRadians(target.lat()));

    final double latitude = Math.sin((rP2.lat() - rP1.lat()) / 2);
    final double longitude = Math.sin((rP2.lon() - rP1.lat()) / 2);

    final Coordination d = Coordination.of(longitude, latitude);

    final var a = d.lat() * d.lat() + d.lon() * d.lon() * Math.cos(rP1.lat()) * Math.cos(rP2.lat());

    final var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c;
  }

  /**
   * Returns a random point in a region bounded by two concentric circles (annulus).
   * <p>
   * centerPoint - the center point of both circles. innerRadius - the radius of the smaller circle. outerRadius - the radius of
   * the larger circle
   */
  public Coordination randomAnnulusPoint(final Coordination centerPoint, final int innerRadius, final int outerRadius) {
    if (innerRadius >= outerRadius) {
      throw new IllegalArgumentException("innerRadius " + innerRadius + "should be smaller than outerRadius" + outerRadius);
    }

    final var deltaRadius = outerRadius - innerRadius;

    final double radius = innerRadius + Math.sqrt(random.nextDouble(0, 1)) * deltaRadius;

    return this.randomCircumferencePoint(centerPoint, (int) radius);
  }

  /**
   * Given a "centerPoint" and a radius "radius", returns a random point that is inside the circle defined by "centerPoint" and
   * "radius". Where -90 <= A <= 90 and -180 <= B <= 180. radius R is in meters. Based on: @see <a
   * href="http://mathworld.wolfram.com/DiskPointPicking.html">...</a>
   */
  public Coordination randomCirclePoint(final Coordination centerPoint, final int radius) {
    return this.randomCircumferencePoint(centerPoint, (int) (Math.sqrt(random.nextDouble(0, 1)) * radius));
  }

  /**
   * Given a "centerPoint" and a radius "radius", returns a random point that is on the circumference defined by "centerPoint" and
   * "radius". Where -90 <= A <= 90 and -180 <= B <= 180. radius is in meters. Based on: @see <a
   * href="http://www.movable-type.co.uk/scripts/latlong.html#destPoint">...</a>
   */
  public Coordination randomCircumferencePoint(final Coordination centerPoint, final int radius) {
    final var sinLat = Math.sin(toRadians(centerPoint.lat()));
    final var cosLat = Math.cos(toRadians(centerPoint.lat()));

    // Random bearing (direction out 360 degrees)
    final var bearing = random.nextDouble(0, 1) * TWO_PI;
    final var sinBearing = Math.sin(bearing);
    final var cosBearing = Math.cos(bearing);

    // Theta is the approximated angular distance
    final var theta = radius / EARTH_RADIUS;
    final var sinTheta = Math.sin(theta);
    final var cosTheta = Math.cos(theta);

    final var rLatitude = Math.asin(sinLat * cosTheta + cosLat * sinTheta * cosBearing);

    var rLongitude = toRadians(centerPoint.lon()) + Math.atan2(
        sinBearing * sinTheta * cosLat,
        cosTheta - sinLat * Math.sin(rLatitude)
    );

    // Normalize longitude L such that -PI < L < +PI
    rLongitude = ((rLongitude + THREE_PI) % TWO_PI) - Math.PI;

    return Coordination.of(toDegrees(rLongitude), toDegrees(rLatitude));
  }

  private static double toRadians(final double deg) {
    return deg * DEG_TO_RAD;
  }

  private static double toDegrees(final double rad) {
    return rad / DEG_TO_RAD;
  }
}
