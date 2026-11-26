
package br.com.pegasus.registration.infras.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configura as regras de segurança e autenticação da aplicação utilizando JWT.
 *
 * <p>
 * Define os endpoints públicos, exige autenticação para os demais recursos
 * e habilita a autorização em nível de método por meio de anotações como
 * {@code @PreAuthorize}.
 */
@Slf4j
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class JwtConfigSecurity {

  private final JwtTokenSecurity configSecurity;

  /**
   * Configura a cadeia de filtros de segurança da aplicação.
   *
   * <p>
   * Desabilita a proteção CSRF, define as regras de autorização e configura
   * a validação dos tokens JWT.
   *
   * @param http configuração HTTP utilizada pelo Spring Security.
   * @return cadeia de filtros de segurança configurada.
   * @throws Exception caso ocorra algum erro durante a configuração.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, Environment env) throws Exception {

    String[] publicEndpoints = Binder.get(env)
        .bind("app.security.public-endpoints", Bindable.listOf(String.class))
        .orElseGet(Collections::emptyList)
        .toArray(String[]::new);

    SecurityFilterChain securityFilterChain = http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(publicEndpoints).permitAll()
            .anyRequest().authenticated()) // Demais endpoints exigem autenticação
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(configSecurity.jwtDecoder())))
        .build();

    log.info("[*] endpoints enabled: {}",
        Arrays.stream(publicEndpoints).collect(Collectors.joining(", ")));
    log.info("[*] configured application security filter chain");
    return securityFilterChain;
  }
}
