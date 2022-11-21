package digital.and.sunshine.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record RadiationDetail(Radiation radiation, @JsonProperty("dt") Instant dateTime) {
}
