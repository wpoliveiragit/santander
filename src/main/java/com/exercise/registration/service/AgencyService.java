package com.exercise.registration.service;

import com.exercise.registration.repository.AgencyRepository;
import com.exercise.registration.repository.entity.AgencyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/** Serviço que gerencia o cadastro e cálculo de distâncias entre agências e usuários. */
@Service
@RequiredArgsConstructor
public class AgencyService {

  private final AgencyRepository repository;
  private final AtomicInteger requestCount = new AtomicInteger(0);

  /** Cadastra uma nova agência e limpa o cache existente. */
  @CacheEvict(value = "agenciesCache", allEntries = true)
  public AgencyEntity register(AgencyEntity agency) {
    return repository.save(agency);
  }

  /** Calcula e ordena as distâncias do usuário até as agências cadastradas. */
  public Map<String, Double> calculateDistances(double userX, double userY) {
    checkRefresh();
    List<AgencyEntity> agencies = listAgencies();

    return agencies.stream().collect(Collectors.toMap(//
                entityModel -> "AGENCY_" + entityModel.getId(),// chave
                entityModel -> euclideanDistance(userX, userY, entityModel.getPosX(), entityModel.getPosY())// valor
            )//
        ).entrySet().stream().sorted(Map.Entry.comparingByValue())//
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
  }

  /** Retorna todas as agências (do cache, se disponível). */
  @Cacheable(value = "agenciesCache")
  public List<AgencyEntity> listAgencies() {
    return repository.findAll();
  }

  /** Limpa o cache automaticamente a cada 10 minutos. */
  @CacheEvict(value = "agenciesCache", allEntries = true)
  @Scheduled(fixedRate = 5 * 1000 * 60)
  public void clearCache() {
    System.out.println("⮌ AgenciesCache evicted (scheduled).");
  }

  /** Calcula a distância euclidiana entre dois pontos cartesianos. <pre><code>√((x1 - x2)² + (y1 - y2)²)</code></pre> */
  private double euclideanDistance(double userX, double userY, double entityX, double entityY) {
    double dx = userX - entityX;
    double dy = userY - entityY;
    return Math.sqrt(dx * dx + dy * dy);
  }

  /** Se atingir 10 requisições, limpa o cache e reinicia o contador */
  private void checkRefresh() {
    if (requestCount.incrementAndGet() > 11) {
      clearCache();
      requestCount.set(0);
      System.out.println("⮌ AgenciesCache evicted (10 requests reached).");
    }
  }
}
