package br.com.pegasus.registration.lib.cache.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import com.github.benmanes.caffeine.cache.Caffeine;

import br.com.pegasus.registration.lib.cache.constants.CacheConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuração base do mecanismo de cache utilizando Caffeine.
 *
 * <p>
 * Define as políticas de expiração e o limite máximo de entradas,
 * permitindo a personalização desses parâmetros por meio de propriedades
 * externas da aplicação.
 *
 * <p>
 * Permite que configurações específicas da aplicação estendam esta classe
 * para reutilizar sua implementação.
 */
@Slf4j
public abstract class CacheConfiguration {

  @Value("${app.cache.expire-after-write:5}")
  private long expireAfterWrite;

  @Value("${app.cache.maximum-size:10000}")
  private long maximumSize;

  /**
   * Cria e configura o gerenciador de cache da aplicação.
   *
   * <p>
   * Define o tempo de expiração e a quantidade máxima de entradas
   * permitidas no cache.
   *
   * @return gerenciador de cache configurado com Caffeine.
   */
  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager manager = new CaffeineCacheManager(CacheConstants.CACHE_NAME);

    manager.setCaffeine(Caffeine.newBuilder()
        .expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES)
        .maximumSize(maximumSize));
    log.info("cacheManager configured");
    return manager;
  }
}
