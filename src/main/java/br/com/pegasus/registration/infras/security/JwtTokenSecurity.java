package br.com.pegasus.registration.infras.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/** JWT (JSON Web Token) */
@Slf4j
@Configuration
public class JwtTokenSecurity {

  private final RSAPublicKey publicKey;
  private final RSAPrivateKey privateKey;

  public JwtTokenSecurity() throws Exception {
    // Gera um par de chaves RSA de 2048 bits
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);

    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    this.publicKey = (RSAPublicKey) keyPair.getPublic();
    this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
  }

  @Bean
  public JwtDecoder jwtDecoder() {// valida
    NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
    log.info("generated jwtDecoder");
    return nimbusJwtDecoder;
  }

  @Bean
  public JwtEncoder jwtEncoder() {// gera
    RSAKey rsaKey = new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();

    NimbusJwtEncoder nimbusJwtEncoder = new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(rsaKey)));
    log.info("generated jwtDecoder");
    return nimbusJwtEncoder;
  }

}
