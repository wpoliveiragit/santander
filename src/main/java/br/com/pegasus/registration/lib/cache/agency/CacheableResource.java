package br.com.pegasus.registration.lib.cache.agency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cache.annotation.Cacheable;

import br.com.pegasus.registration.lib.cache.constants.CacheConstants;

/**
 * Anotação personalizada para armazenar resultados de operações em cache.
 *
 * <p>
 * Encapsula a anotação {@link Cacheable}, utilizando o cache
 * {@code agenciesCache} para armazenar os resultados dos métodos anotados
 * e evitar execuções desnecessárias.
 *
 * <p>
 * Quando existe um resultado correspondente no cache, ele é retornado
 * sem executar novamente o método. Caso contrário, o método é executado
 * e seu resultado é armazenado para consultas futuras.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Cacheable(CacheConstants.CACHE_NAME)
public @interface CacheableResource {
}
