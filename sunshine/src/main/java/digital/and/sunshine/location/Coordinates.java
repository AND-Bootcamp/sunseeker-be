package digital.and.sunshine.location;

public record Coordinates(double lon, double lat) {

  public static Coordinates of(final double lon, final double lat) {
    return new Coordinates(lon, lat);
  }
}