package br.com.pegasus.registration.domain.adapter;

import java.util.Map;

public interface JwtAuthAdapter {

  Map<String, String> generateToken(String username);

}
