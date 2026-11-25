package br.com.santander.registration.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configura as regras de segurança e autenticação da aplicação utilizando JWT.
 *
 * Define os endpoints públicos, exige autenticação para os demais recursos
 * e habilita a autorização em nível de método por meio de anotações
 * como @PreAuthorize.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class JwtConfigSecurity {

  private final JwtTokenSecurity configSecurity;

      /**
     * Configura a cadeia de filtros de segurança da aplicação.
     *
     * Desabilita a proteção CSRF, define as regras de autorização e configura
     * a validação dos tokens JWT.
     *
     * @param http configuração HTTP utilizada pelo Spring Security.
     * @return cadeia de filtros de segurança configurada.
     * @throws Exception caso ocorra algum erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth/token").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(configSecurity.jwtDecoder())))
                .build();
    }

}
