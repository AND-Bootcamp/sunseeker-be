package digital.and.sunshine.sunseeker;

import digital.and.sunshine.weather.provider.openweathermap.model.Coordination;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sun-seeker")
@RequiredArgsConstructor
public class SunSeekerController {

  private final SunSeekerService sunSeekerService;

  @GetMapping
  public ResponseEntity<List<SunnyLocation>> seekSunnyLocations(
      @RequestParam("lat") final double lat,
      @RequestParam("lon") final double lon) {

    final Coordination coor = new Coordination(lon, lat);

    final var solarRadiationResponse = this.sunSeekerService.seekSunnyLocations(coor);

    return ResponseEntity.ok(solarRadiationResponse);
  }
}
