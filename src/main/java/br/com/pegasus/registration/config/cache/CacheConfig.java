package br.com.pegasus.registration.config.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import br.com.pegasus.registration.infrastructure.cache.configuration.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig extends CacheConfiguration {
}
