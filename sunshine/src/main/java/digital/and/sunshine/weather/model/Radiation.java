package digital.and.sunshine.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Radiation(
    @JsonProperty("ghi")
    double globalHorizontalIrradiance,
    @JsonProperty("dni")
    double directNormalIrradiance,
    @JsonProperty("dhi")
    double diffuseHorizontalIrradiance,
    @JsonProperty("ghi_cs")
    double globalHorizontalIrradianceClearSky,
    @JsonProperty("dni_cs")
    double directNormalIrradianceClearSky,
    @JsonProperty("dhi_cs")
    double diffuseHorizontalIrradianceClearSky) {

}