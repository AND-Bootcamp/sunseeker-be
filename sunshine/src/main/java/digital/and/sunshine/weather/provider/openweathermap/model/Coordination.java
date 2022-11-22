package digital.and.sunshine.weather.provider.openweathermap.model;

public record Coordination(double lon, double lat) {

  public static Coordination of(final double lon, final double lat) {
    return new Coordination(lon, lat);
  }
}