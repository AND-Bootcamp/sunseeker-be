package and.digital.sunshine.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SunniestSpot {

    String latitude;
    String longitude;
    String title;
    String subtitle;
}
