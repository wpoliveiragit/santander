package br.com.pegasus.registration.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.core.env.EnumerablePropertySource;

@Slf4j
@Component
public class ApplicationStartupConfig implements ApplicationRunner {

  private final ConfigurableEnvironment environment;

  public ApplicationStartupConfig(ConfigurableEnvironment environment) {
    this.environment = environment;
  }

  @Override
  public void run(ApplicationArguments args) {
    System.out.println("\n");
    for (PropertySource<?> propertySource : environment.getPropertySources()) {
      String name = propertySource.getName();
      if (name.contains(".env")) {
        getproperties(propertySource, "LOCAL ENVIRONMENT VARIABLES FOUND", ".env");
      }
      if (name.contains("application-internal")) {
        getproperties(propertySource, "PROPERTIES OF", "application-internal");
      }
    }
  }

  private void getproperties(PropertySource<?> propertySource, String msg, String local) {
    log.info("{}: [{}]", msg, local);
    if (propertySource instanceof EnumerablePropertySource<?> enumerable) {
      for (String propertyName : enumerable.getPropertyNames()) {
        log.info("- {}={}", propertyName, environment.getProperty(propertyName));
      }
      System.out.println();
    }
  }

}
