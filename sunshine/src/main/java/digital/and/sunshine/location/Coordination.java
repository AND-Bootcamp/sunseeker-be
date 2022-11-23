package digital.and.sunshine.location;

public record Coordination(double lon, double lat) {

  public static Coordination of(final double lon, final double lat) {
    return new Coordination(lon, lat);
  }
}