package digital.and.sunshine.weather.provider.openweathermap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RadiationDetail(Radiation radiation, @JsonProperty("dt") long dateTime) {

}
