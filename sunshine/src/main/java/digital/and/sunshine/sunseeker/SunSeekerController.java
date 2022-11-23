package digital.and.sunshine.sunseeker;

import digital.and.sunshine.location.Coordinates;
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
  public ResponseEntity<SunDetailsForLocations> seekSunnyLocations(
      @RequestParam("lat") final double lat,
      @RequestParam("lon") final double lon) {

    final Coordinates coor = new Coordinates(lon, lat);

    final var sunnyLocations = this.sunSeekerService.seekSunnyLocations(coor);

    return ResponseEntity.ok(sunnyLocations);
  }
}
