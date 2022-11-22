package digital.and.sunshine.sunseeker;

import java.util.List;

public record SunnyLocations(SunnyLocation userLocation, List<SunnyLocation> alternatives) {

}
