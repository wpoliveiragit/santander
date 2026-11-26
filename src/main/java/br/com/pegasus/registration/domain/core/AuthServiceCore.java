package br.com.pegasus.registration.domain.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.pegasus.registration.domain.adapter.JwtAuthAdapter;
import br.com.pegasus.registration.domain.port.AuthServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceCore implements AuthServicePort {

  private final JwtAuthAdapter jwtAuth;

  @Value("${app.security.jwt.expiration}")
  private long expiration;

  public Map<String, String> generateToken(String username) {
    return jwtAuth.generateToken(username);
  }

}
