package digital.and.sunshine.weather.model;

public record Coordination(double lon, double lat) {

  public static Coordination of(final double lon, final double lat) {
    return new Coordination(lon, lat);
  }
}