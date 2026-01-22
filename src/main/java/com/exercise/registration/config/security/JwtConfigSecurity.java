package com.exercise.registration.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtConfigSecurity {

  private final JwtTokenSecurity configSecurity;

  @Bean
  public SecurityFilterChain securityFilterChain(Environment env, HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)//
        .authorizeHttpRequests(auth -> auth.requestMatchers(getAllowedEndpoints(env))//
            .permitAll()//
            .anyRequest()//
            .authenticated()//
        ).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(configSecurity.jwtDecoder())))//
        .build();
  }

  private String[] getAllowedEndpoints(Environment env) {
    if (env.getProperty("spring.profiles.active", "default").equalsIgnoreCase("local-dev")) {
      // Ambiente de teste local, libera todos os endpoints
      return new String[]{//
          "/**"//
      };
    }
    // ambiente
    return new String[]{"/oauth/token"};
  }

}
