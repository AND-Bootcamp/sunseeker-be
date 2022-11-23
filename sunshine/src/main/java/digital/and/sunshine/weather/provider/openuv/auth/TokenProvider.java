package digital.and.sunshine.weather.provider.openuv.auth;

import java.util.List;

public interface TokenProvider {

  String current();

  String next();

  List<String> tokens();

}
