package com.exercise.registration.controller;

import com.exercise.registration.repository.entity.AgencyEntity;
import com.exercise.registration.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/** Controlador REST que expõe os endpoints do desafio. */
@RestController
@RequestMapping("/desafio")
@RequiredArgsConstructor
public class AgencyController {

  private final AgencyService service;

  /** Cadastra uma nova agência. Requer o escopo de segurança "write". */
  @PostMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('SCOPE_write')")
  public ResponseEntity<AgencyEntity> register(@RequestBody AgencyEntity agency) {
    AgencyEntity saved = service.register(agency);
    return ResponseEntity.ok(saved);
  }

  /** Retorna as distâncias do usuário para todas as agências. Requer o escopo de segurança "read". */
  @GetMapping(value = "/distancia", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('SCOPE_read')")
  public ResponseEntity<Map<String, Double>> calculateDistance(@RequestParam double posX, @RequestParam double posY) {
    Map<String, Double> result = service.calculateDistances(posX, posY);
    return ResponseEntity.ok(result);
  }

}
