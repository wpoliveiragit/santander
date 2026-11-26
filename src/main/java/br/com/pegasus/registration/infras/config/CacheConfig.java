package br.com.pegasus.registration.infras.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import br.com.pegasus.registration.lib.cache.configuration.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig extends CacheConfiguration {
}
