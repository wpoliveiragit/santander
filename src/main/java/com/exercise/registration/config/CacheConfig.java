package com.exercise.registration.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

/** Configuração do cache com Caffeine. */
@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

  @Bean
  public Caffeine<Object, Object> caffeineConfig() {
    return Caffeine.newBuilder()
        .expireAfterWrite(5, TimeUnit.MINUTES) // expira a cada 5 minutos por item
        .maximumSize(10_000); // limite maximo de entrada
  }

  @Bean
  public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
    CaffeineCacheManager manager = new CaffeineCacheManager("agenciesCache");
    manager.setCaffeine(caffeine);
    return manager;
  }
}

