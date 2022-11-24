package digital.and.sunshine.location;

import lombok.Builder;

@Builder
public record RetrieveNearPlaces(Coordinates coordinates, int radius, int numberOfPlaces) {

  public int radiusInMeter() {
    return this.radius * 1000;
  }
}