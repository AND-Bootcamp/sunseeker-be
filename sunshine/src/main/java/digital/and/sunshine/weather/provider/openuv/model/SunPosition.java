package digital.and.sunshine.weather.provider.openuv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SunPosition(@JsonProperty("azimuth")
                          Double azimuth,
                          @JsonProperty("altitude")
                          Double altitude) {
}
