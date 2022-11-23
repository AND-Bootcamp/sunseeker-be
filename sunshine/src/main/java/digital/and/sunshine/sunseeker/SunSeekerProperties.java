package digital.and.sunshine.sunseeker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sun-seeker")
public class SunSeekerProperties {

  private int radiusInKm;
  private int randomNumberOfPlaces;

}
