package digital.and.sunshine.sunseeker;

import digital.and.sunshine.location.LocationSeekerService;
import digital.and.sunshine.weather.SolarRadiationService;
import digital.and.sunshine.weather.model.Coordination;
import digital.and.sunshine.weather.response.SolarRadiationResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SunSeekerService {

  //TODO: should be configurable
  private static final int RADIUS = 500; //meter
  private static final int RANDOM_POINT_SIZE = 10;

  private final LocationSeekerService locationSeekerService;
  private final SolarRadiationService solarRadiationService;

  public List<SolarRadiationResponse> seekSunnyLocations(final Coordination coordination) {

    //TODO: all random generation can be parallel use VirtualThread or StructuredTaskScope for concurrency
    final Set<Coordination> randomCirclePoints = IntStream.range(0, RANDOM_POINT_SIZE)
        .mapToObj(value -> this.locationSeekerService.randomCirclePoint(coordination, RADIUS))
        .collect(Collectors.toSet());

    final Set<Coordination> randomCircumferencePoints = IntStream.range(0, 10)
        .mapToObj(value -> this.locationSeekerService.randomCircumferencePoint(coordination, RADIUS))
        .collect(Collectors.toSet());

    final Set<Coordination> randomAnnulusPoint = IntStream.range(0, 10)
        .mapToObj(value -> this.locationSeekerService.randomAnnulusPoint(coordination, RADIUS / 2, RADIUS))
        .collect(Collectors.toSet());

    final Set<Coordination> randomMixedCoordination = Stream.concat(
            Stream.concat(randomCirclePoints.stream(), randomCircumferencePoints.stream()), randomAnnulusPoint.stream())
        .collect(Collectors.toSet());

    //TODO: filter by radiation and transform to list of SunnyLocation.java
    // SolarRadiationResponse can be Comparable for sorting
    return randomMixedCoordination.parallelStream()
        .map(this.solarRadiationService::retrieveByCoordination)
        .toList();


  }
}
