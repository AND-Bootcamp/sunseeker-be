package digital.and.sunshine.sunseeker;

import digital.and.sunshine.location.Coordinates;
import digital.and.sunshine.weather.provider.openuv.model.SunResponse;

public record SunDetailsForLocation(Coordinates coordinates, UVDetail uvDetail) implements Comparable<SunDetailsForLocation> {

  public static SunDetailsForLocation from(final Coordinates coor, final SunResponse sunResponse) {
    final Double uv = sunResponse.result().uv();
    final UVLevel uvLevel = UVLevel.from(uv);

    final UVDetail uvDetail1 = new UVDetail(uvLevel, uv);

    return new SunDetailsForLocation(coor, uvDetail1);
  }


  @Override
  public int compareTo(final SunDetailsForLocation o) {
    return Double.compare(this.uvDetail().uvValue(), o.uvDetail().uvValue());
  }
}
