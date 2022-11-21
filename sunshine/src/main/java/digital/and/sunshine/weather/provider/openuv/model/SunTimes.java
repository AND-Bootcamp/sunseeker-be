package digital.and.sunshine.weather.provider.openuv.model;

import java.time.Instant;

public record SunTimes(Instant solarNoon,
                       Instant nadir,
                       Instant sunrise,
                       Instant sunset,
                       Instant sunriseEnd,
                       Instant sunsetStart,
                       Instant dawn,
                       Instant dusk,
                       Instant nauticalDawn,
                       Instant nauticalDusk,
                       Instant nightEnd,
                       Instant night,
                       Instant goldenHourEnd,
                       Instant goldenHour) {

}
