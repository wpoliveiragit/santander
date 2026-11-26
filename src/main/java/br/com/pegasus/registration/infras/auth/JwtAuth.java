package br.com.pegasus.registration.infras.auth;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import br.com.pegasus.registration.domain.adapter.JwtAuthAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Component
@RequiredArgsConstructor
public class JwtAuth implements JwtAuthAdapter {

  private final JwtEncoder encoder;

  @Value("${app.security.jwt.expiration}")
  private long expiration;

  @Override
  public Map<String, String> generateToken(String username) {
    Instant now = Instant.now();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(username)
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiration))
        .claim("scope", "read write")
        .build();

    String token = encoder
        .encode(JwtEncoderParameters.from(claims))
        .getTokenValue();

    log.info("New token generated for user {}", username);

    return Map.of("access_token", token);
  }

}
