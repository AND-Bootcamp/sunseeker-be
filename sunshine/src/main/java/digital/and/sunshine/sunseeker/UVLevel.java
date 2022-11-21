package digital.and.sunshine.sunseeker;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UVLevel {

  LOW,
  MODERATE,
  HIGH,
  VERY_HIGH,
  EXTREME;


  public static UVLevel from(final Double uv) {
    if (uv == null || uv < 3) {
      return LOW;
    }

    if (uv < 6) {
      return MODERATE;
    }

    if (uv < 8) {
      return HIGH;
    }

    if (uv < 11) {
      return VERY_HIGH;
    }

    return EXTREME;
  }

}
