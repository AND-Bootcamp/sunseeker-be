package and.digital.sunshine.controller;

import and.digital.sunshine.dto.SunniestSpot;
import and.digital.sunshine.service.SunSeekerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SunSeekerController {

    @Autowired
    SunSeekerService sunSeekerCalculator;

    @GetMapping("/seeksun")
    public SunniestSpot seekSunnySpot(@RequestParam(name = "latitude") String latitude, @RequestParam(name = "longitude") String longitude) {
        return sunSeekerCalculator.getPosition(latitude, longitude);
    }

}