
package br.com.pegasus.registration.controller;

import java.time.Instant;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller responsável pelas operações de autenticação da aplicação.
 *
 * <p>
 * Disponibiliza endpoints para geração de tokens JWT utilizados
 * na autenticação e autorização das requisições.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
@Tag(name = "Autenticação", description = "Operações relacionadas à autenticação e geração de tokens JWT.")
public class AuthController {

  private final JwtEncoder encoder;

  @Value("${app.security.jwt.expiration}")
  private long expiration;

  /**
   * Gera um token JWT para o usuário informado.
   *
   * <p>
   * O token contém:
   * <ul>
   * <li>Subject: identificação do usuário.</li>
   * <li>Issued At: instante de emissão.</li>
   * <li>Expires At: instante de expiração.</li>
   * <li>Scope: permissões read e write.</li>
   * </ul>
   *
   * @param username identificação do usuário para o qual o token será gerado.
   * @return mapa contendo o token JWT no atributo access_token.
   */
  @Operation(summary = "Gerar token JWT", description = "Gera um token JWT para o usuário informado, definindo suas permissões e validade.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Token gerado com sucesso.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(type = "object", example = "{\"access_token\": \"<JWT_TOKEN>\"}"))),
      @ApiResponse(responseCode = "400", description = "Parâmetro username ausente ou inválido."),
      @ApiResponse(responseCode = "500", description = "Erro interno ao gerar o token.")
  })
  @PostMapping("/token")
  public Map<String, String> token(
      @Parameter(description = "Identificação do usuário.", example = "usuario") @RequestParam("username") String username) {

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
