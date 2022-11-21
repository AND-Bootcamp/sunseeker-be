package digital.and.sunshine.weather.provider.openuv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record Result(@JsonProperty("uv")
                     Double uv,
                     @JsonProperty("uv_time")
                     Instant uvTime,
                     @JsonProperty("uv_max")
                     Double uvMax,
                     @JsonProperty("uv_max_time")
                     Instant uvMaxTime,
                     @JsonProperty("ozone")
                     Double ozone,
                     @JsonProperty("ozone_time")
                     Instant ozoneTime,
                     @JsonProperty("safe_exposure_time")
                     SafeExposureTime safeExposureTime,
                     @JsonProperty("sun_info")
                     SunInfo sunInfo) {


}
