package br.com.pegasus.registration.domain.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.pegasus.registration.domain.port.AgencyServicePort;
import br.com.pegasus.registration.infras.common.MethodCommon;
import br.com.pegasus.registration.infras.repository.AgencyRepository;
import br.com.pegasus.registration.infras.repository.entity.AgencyEntity;
import br.com.pegasus.registration.lib.cache.agency.CacheEvictResource;
import br.com.pegasus.registration.lib.cache.agency.CacheableResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço que gerencia o cadastro e cálculo de distâncias entre agências e
 * usuários.
 */
@Slf4j
@Service
@EnableScheduling // Normalmento é adicionado na classe do metodo main
@RequiredArgsConstructor
public class AgencyServiceCore implements AgencyServicePort{

  private final AgencyRepository repository;
  private final AtomicInteger requestCount = new AtomicInteger(0);

  /** Cadastra uma nova agência e limpa o cache existente. */
  @CacheEvictResource
  @Override
  public AgencyEntity register(AgencyEntity agency) {
    AgencyEntity entity = repository.save(agency);
    log.info("registered item {}", MethodCommon.objToJson(entity));
    return entity;
  }

  /** Calcula e ordena as distâncias do usuário até as agências cadastradas. */
  @Override
  public Map<String, Double> calculateDistances(double userX, double userY) {
    checkRefresh();
    LinkedHashMap<String, Double> collect = listAgencies().stream()
        .collect(Collectors.toMap(
            eKey -> "AGENCY_" + eKey.getId(),
            eVal -> euclideanDistance(userX, userY, eVal.getPosX(), eVal.getPosY())))
        .entrySet().stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (v1, v2) -> v1,
            LinkedHashMap::new));
    log.info("Distances from agencies calculated for ({},{}) coordinates :{}", userX, userY, collect);
    return collect;
  }

  /** Retorna todas as agências (do cache, se disponível). */
  @CacheableResource
  public List<AgencyEntity> listAgencies() {
    List<AgencyEntity> list = repository.findAll();
    log.info("List of agencies retrieved: {}", list);
    return list;
  }

  /** Limpa o cache automaticamente a cada 1 minutos. */
  @CacheEvictResource
  @Scheduled(fixedRate = 1 * 1000 * 60)
  public void clearCache() {// metodo com scheduled para que também seja executado de maneira independente
    log.info("(scheduled) AgenciesCache evicted.");
  }

  /**
   * Calcula a distância euclidiana entre dois pontos cartesianos.
   *
   * <pre>
   * <code>√((x1 - x2)² + (y1 - y2)²)</code>
   * </pre>
   */
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
      log.info("⮌ AgenciesCache evicted (10 requests reached).");
    }
  }
}
