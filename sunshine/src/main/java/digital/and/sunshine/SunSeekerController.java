package digital.and.sunshine;

import digital.and.sunshine.weather.SolarRadiationClient;
import digital.and.sunshine.weather.response.SolarRadiationResponse;
import digital.and.sunshine.weather.model.Coordination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sun-seeker")
@RequiredArgsConstructor
public class SunSeekerController {

    private final SolarRadiationClient solarRadiationClient;

    @GetMapping("/test")
    public ResponseEntity<SolarRadiationResponse> test() {
        Coordination coor = new Coordination(-77.0364, 38.8951);
        var solarRadiationResponse = solarRadiationClient.retrieveByCoordination(coor);

        return ResponseEntity.ok(solarRadiationResponse);
    }
}
