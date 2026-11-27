package br.com.pegasus.registration.infras.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppInfoConfig implements ApplicationRunner {

  private final ConfigurableEnvironment environment;

  @Override
  public void run(ApplicationArguments args) {
    System.out.println();
    findsFiles();
    logProperties();
    System.out.println();
  }

  private void findsFiles() {
    List<String> nameFiles = new ArrayList<>(List.of(
        ".env",
        "application-internal"));

    for (PropertySource<?> propertySource : environment.getPropertySources()) {
      if (nameFiles.isEmpty()) {
        break;
      }
      String name = propertySource.getName();
      for (String nf : nameFiles) {
        if (name.contains(nf)) {
          nameFiles.remove(nf);
          log.info("* Arquivo de '{}' carregado", nf);
          break;
        }
      }
    }
  }

  private void logProperties() {
    logGroup("APPLICATION.YML",
        "spring.application.name",
        "spring.profiles.active",
        "server.servlet.context-path",
        "server.port",
        "spring.datasource.url",
        "spring.datasource.driver-class-name",
        "spring.datasource.username",
        "spring.datasource.password",
        "spring.jpa.hibernate.ddl-auto",
        "spring.jpa.show-sql",
        "spring.h2.console.enabled",
        "spring.h2.console.path",
        "cache.type");

    logGroup("APPLICATION-INTERNAL.YML",
        "app.security.jwt.expiration",
        "app.cache.expire-after-write",
        "app.security.public-endpoints",
        "app.cache.maximum-size");
  }

  private void logGroup(String group, String... properties) {
    System.out.println();
    log.info("== {} ==================", group);
    for (String property : properties) {
      Object value = getPropertyValue(property);
      log.info("* {} = {}", property, value);
    }
  } 

  private Object getPropertyValue(String property) {
    return Binder.get(environment)
        .bind(property, Bindable.listOf(String.class))
        .map(list -> String.join(", ", list))
        .orElse(environment.getProperty(property));
  }

}
