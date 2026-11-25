package br.com.pegasus.registration.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.pegasus.registration.infrastructure.common.MethodCommon;
import br.com.pegasus.registration.repository.entity.AgencyEntity;
import br.com.pegasus.registration.service.AgencyService;

import java.util.Map;

/** Controlador REST que expõe os endpoints do desafio. */
@Slf4j
@RestController
@RequestMapping("/desafio")
@RequiredArgsConstructor
public class AgencyController {

  private final AgencyService service;

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
