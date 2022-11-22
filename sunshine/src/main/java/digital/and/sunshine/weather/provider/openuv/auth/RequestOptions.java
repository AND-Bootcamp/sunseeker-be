package digital.and.sunshine.weather.provider.openuv.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "openuv-client.request-options")
public class RequestOptions {

  private String baseUrl;
  private String[] tokens;

}