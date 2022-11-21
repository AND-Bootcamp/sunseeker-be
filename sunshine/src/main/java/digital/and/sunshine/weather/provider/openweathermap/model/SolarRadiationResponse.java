package digital.and.sunshine.weather.provider.openweathermap.model;

import java.util.List;

public record SolarRadiationResponse(Coordination coord, List<RadiationDetail> list) {

}
