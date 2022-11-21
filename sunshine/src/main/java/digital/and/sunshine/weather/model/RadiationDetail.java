package digital.and.sunshine.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RadiationDetail(Radiation radiation, @JsonProperty("dt") long dateTime) {

}
