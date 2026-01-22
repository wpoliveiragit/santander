package com.exercise.registration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class AuthController {

  private final JwtEncoder encoder;
  private final Instant instantNow = Instant.now();

  @PostMapping("/token")
  public Map<String, String> token(@RequestParam String username) {

    System.out.println(Instant.now().toString());
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(username)
        .issuedAt(instantNow)
        .expiresAt(instantNow.plusSeconds(60))
        .claim("scope", "read write")
        .build();
    return Map.of("access_token", encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
  }
}