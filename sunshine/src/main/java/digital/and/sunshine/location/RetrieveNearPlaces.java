package digital.and.sunshine.location;

import lombok.Builder;

@Builder
public record RetrieveNearPlaces(Coordinates coordinates, int radius, int numberOfPlaces) {

}