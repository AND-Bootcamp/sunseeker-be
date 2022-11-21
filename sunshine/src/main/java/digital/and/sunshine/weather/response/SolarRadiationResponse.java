package digital.and.sunshine.weather.response;

import digital.and.sunshine.weather.model.Coordination;
import digital.and.sunshine.weather.model.RadiationDetail;

import java.util.List;

public record SolarRadiationResponse(Coordination coord, List<RadiationDetail> list) {
}
