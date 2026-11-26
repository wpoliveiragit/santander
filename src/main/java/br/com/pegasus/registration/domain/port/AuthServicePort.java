package br.com.pegasus.registration.domain.port;

import java.util.Map;

public interface AuthServicePort {

  Map<String, String> generateToken(String username);

}
