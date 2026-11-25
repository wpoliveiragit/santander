
package br.com.pegasus.registration.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Configura as regras de segurança e autenticação da aplicação utilizando JWT.
 *
 * <p>Define os endpoints públicos, exige autenticação para os demais recursos
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
   * <p>Desabilita a proteção CSRF, define as regras de autorização e configura
   * a validação dos tokens JWT.
   *
   * @param http configuração HTTP utilizada pelo Spring Security.
   * @return cadeia de filtros de segurança configurada.
   * @throws Exception caso ocorra algum erro durante a configuração.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    SecurityFilterChain securityFilterChain = http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            // Endpoints públicos
            .requestMatchers(
                "/oauth/token",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml",
                "/swagger-ui/**",
                "/swagger-ui.html"
            ).permitAll()

            // Demais endpoints exigem autenticação
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.decoder(configSecurity.jwtDecoder())))
        .build();

    log.info("configured application security filter chain");
    return securityFilterChain;
  }
}
