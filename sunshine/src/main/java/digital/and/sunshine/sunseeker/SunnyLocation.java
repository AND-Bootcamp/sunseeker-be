package digital.and.sunshine.sunseeker;

import digital.and.sunshine.weather.provider.openuv.model.SunResponse;
import digital.and.sunshine.weather.provider.openweathermap.model.Coordination;

public record SunnyLocation(Coordination coordination, UVLevel uvLevel) implements Comparable<SunnyLocation> {

  public static SunnyLocation from(final Coordination coor, final SunResponse sunResponse) {
    final UVLevel uvLevel = UVLevel.from(sunResponse.result().uv());

    return new SunnyLocation(coor, uvLevel);
  }

  @Override
  public int compareTo(final SunnyLocation o) {
    return this.uvLevel.compareTo(o.uvLevel());
  }
}
