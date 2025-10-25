package com.exercise.registration.controller;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class AuthController {

  private final JwtEncoder encoder;

  public AuthController(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  @PostMapping("/token")
  public Map<String, String> token(@RequestParam String username) {
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(username)
        .issuedAt(Instant.now())
//        .expiresAt(Instant.now().plusSeconds(3600))
        .claim("scope", "read write")
        .build();

    String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    return Map.of("access_token", token);
  }
}