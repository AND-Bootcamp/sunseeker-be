package digital.and.sunshine.sunseeker;

import digital.and.sunshine.location.Coordinates;
import digital.and.sunshine.location.LocationSeekerService;
import digital.and.sunshine.location.Places;
import digital.and.sunshine.location.RetrieveNearPlaces;
import digital.and.sunshine.weather.SunInfoService;
import digital.and.sunshine.weather.provider.openuv.model.SunResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SunSeekerService {

  //TODO: should be configurable
  private static final int RADIUS = 15; //km
  private static final int NUMBER_OF_PLACES = 3;

  private final LocationSeekerService locationSeekerService;
  private final SunInfoService sunInfoService;

  public SunDetailsForLocations seekSunnyLocations(final Coordinates coordinates) {
    //TODO: use google nearly search api instead of random location

    final RetrieveNearPlaces retrieveNearPlaces = RetrieveNearPlaces.builder()
        .radius(RADIUS)
        .numberOfPlaces(NUMBER_OF_PLACES)
        .coordinates(coordinates)
        .build();

    final Places nearPlaces = this.locationSeekerService.getNearPlaces(retrieveNearPlaces);

    final List<SunDetailsForLocation> sunDetailsOfNearPlaces = nearPlaces.coordinates().parallelStream()
        .<SunDetailsForLocation>mapMulti((coor, sunnyLocationConsumer) -> {
          final SunResponse sunResponse = this.sunInfoService.retrieveByCoordination(coordinates);
          sunnyLocationConsumer.accept(SunDetailsForLocation.from(coor, sunResponse));
        })
        .collect(Collectors.toSet())
        .stream().sorted()
        .toList();

    final SunResponse sunResponseByUserLocation = this.sunInfoService.retrieveByCoordination(coordinates);

    final SunDetailsForLocation sunDetailsOfUser = SunDetailsForLocation.from(coordinates, sunResponseByUserLocation);

    return new SunDetailsForLocations(sunDetailsOfUser, sunDetailsOfNearPlaces);
  }

}
