package digital.and.sunshine.sunseeker;

import java.util.List;

public record SunDetailsForLocations(SunDetailsForLocation userLocation, List<SunDetailsForLocation> alternatives) {

}
