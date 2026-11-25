package br.com.santander.registration.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configura o mecanismo de cache da aplicação utilizando Caffeine.
 *
 * Define as políticas de expiração, capacidade e gerenciamento
 * dos caches utilizados pela aplicação.
 */
@Configuration
@EnableCaching
public class CacheConfig {

  private static final long EXPIRE_AFTER_WRITE = 5;
  private static final long MAXIMUM_SIZE = 10_000;
  private static final String AGENCIES_CACHE = "agenciesCache";

  /**
   * Configura as políticas de armazenamento e expiração do cache.
   *
   * @return configuração do Caffeine.
   */
  @Bean
  public Caffeine<Object, Object> caffeineConfig() {
    return Caffeine.newBuilder()
        .expireAfterWrite(EXPIRE_AFTER_WRITE, TimeUnit.MINUTES) // expira a cada 5 minutos por item
        .maximumSize(MAXIMUM_SIZE); // limite maximo de entrada
  }

  /**
   * Configura o gerenciador de cache da aplicação.
   *
   * @param caffeine configuração de armazenamento e expiração do Caffeine.
   * @return gerenciador de cache do Spring.
   */
  @Bean
  public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
    CaffeineCacheManager manager = new CaffeineCacheManager(AGENCIES_CACHE);
    manager.setCaffeine(caffeine);
    return manager;
  }

}
