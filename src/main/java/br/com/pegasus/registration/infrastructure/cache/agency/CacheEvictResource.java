package br.com.pegasus.registration.infrastructure.cache.agency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cache.annotation.CacheEvict;

import br.com.pegasus.registration.infrastructure.cache.constants.CacheConstants;

/**
 * Anotação personalizada para invalidar entradas armazenadas em cache.
 *
 * <p>
 * Encapsula a anotação {@link CacheEvict}, removendo todas as entradas
 * do cache {@code agenciesCache} após a execução bem-sucedida do método
 * anotado.
 *
 * <p>
 * Deve ser utilizada em operações que modificam os dados armazenados,
 * garantindo que consultas posteriores obtenham informações atualizadas.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheEvict(value = CacheConstants.CACHE_NAME, allEntries = true)
public @interface CacheEvictResource {
}
