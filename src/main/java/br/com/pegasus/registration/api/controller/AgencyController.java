package br.com.pegasus.registration.api.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pegasus.registration.domain.port.AgencyServicePort;
import br.com.pegasus.registration.infras.common.MethodCommon;
import br.com.pegasus.registration.infras.repository.entity.AgencyEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Controlador REST que expõe os endpoints do desafio. */
@Slf4j
@RestController
@RequestMapping("/desafio")
@RequiredArgsConstructor
public class AgencyController {

  private final AgencyServicePort service;

  /** Cadastra uma nova agência. Requer o escopo de segurança "write". */
  @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('SCOPE_write')")
  public ResponseEntity<AgencyEntity> register(@RequestBody AgencyEntity agency) {
    log.info("Registration request for agency: {}", MethodCommon.objToJson(agency));
    AgencyEntity saved = service.register(agency);
    return ResponseEntity.ok(saved);
  }

  /**
   * Retorna as distâncias do usuário para todas as agências. Requer o escopo de
   * segurança "read".
   */
  @GetMapping(value = "/distancia", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('SCOPE_read')")
  public ResponseEntity<Map<String, Double>> calculateDistance(@RequestParam double posX, @RequestParam double posY) {
    log.info("Request for distance calculation for ({},{}) coordinates", posX, posY);
    Map<String, Double> result = service.calculateDistances(posX, posY);
    return ResponseEntity.ok(result);
  }

}
