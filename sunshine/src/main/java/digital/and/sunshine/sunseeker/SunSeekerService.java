package digital.and.sunshine.sunseeker;

import digital.and.sunshine.location.LocationSeekerService;
import digital.and.sunshine.weather.SunInfoService;
import digital.and.sunshine.weather.provider.openuv.model.SunResponse;
import digital.and.sunshine.location.Coordination;
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
  private final SunInfoService sunInfoService;

  public SunnyLocations seekSunnyLocations(final Coordination coordination) {

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

    final List<SunnyLocation> sunnyLocations = randomMixedCoordination.parallelStream()
        .<SunnyLocation>mapMulti((coor, sunnyLocationConsumer) -> {
          final SunResponse sunResponse = this.sunInfoService.retrieveByCoordination(coordination);
          sunnyLocationConsumer.accept(SunnyLocation.from(coor, sunResponse));
        })
        .collect(Collectors.toSet())
        .stream().sorted()
        .toList();

    final SunResponse sunByUserLocation = this.sunInfoService.retrieveByCoordination(coordination);

    return new SunnyLocations(SunnyLocation.from(coordination, sunByUserLocation), sunnyLocations);
  }

}
