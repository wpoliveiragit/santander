# Guia de utilização --- Spring Caffeine Cache

Este guia demonstra como adicionar e utilizar a biblioteca `spring-caffeine-cache` em uma aplicação Spring Boot com Maven.

A biblioteca encapsula a configuração do Caffeine e disponibiliza as anotações `@CacheableResource` e `@CacheEvictResource` para uso nas regras de negócio. O nome do cache é centralizado na biblioteca (`applicationCache`), não sendo necessário informá-lo nas classes consumidoras.

## 1. Adicionar a dependência

Adicione a dependência ao `pom.xml` da aplicação. O exemplo utiliza coordenadas fictícias; substitua os valores pelos publicados pela biblioteca.

```xml
<dependency>
    <groupId>br.com.banco</groupId>
    <artifactId>spring-caffeine-cache</artifactId>
    <version>1.0.0</version>
</dependency>
```

A biblioteca deve disponibilizar suas dependências necessárias, incluindo Caffeine e Spring Cache, de forma transitiva.

## 2. Configurar o `application.yml` (opcional)

A biblioteca permite personalizar o tempo de expiração e a quantidade máxima de entradas do cache.

```yaml
app:
  cache:
    expire-after-write: 5
    maximum-size: 10000
```

| Propriedade                    | Descrição                                                          | Valor padrão |
| ------------------------------ | ------------------------------------------------------------------ | ------------ |
| `app.cache.expire-after-write` | Tempo em minutos até uma entrada expirar após ser gravada no cache | `5`          |
| `app.cache.maximum-size`       | Quantidade máxima de entradas mantidas no cache                    | `10000`      |

- **A configuração é opcional.** Se as propriedades não forem informadas no `application.yml`, a biblioteca utilizará os valores padrão.
- A expiração é baseada no momento da gravação (`expireAfterWrite`). Portanto, uma leitura não reinicia o prazo de expiração.

## 3. Ativar a configuração na aplicação

Crie uma classe gerenciada pelo Spring que estenda `CacheConfiguration`, disponibilizada pela biblioteca.

```java
package br.com.banco.registration.config;

import br.com.banco.registration.infrastructure.cache.CacheConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCacheConfiguration extends CacheConfiguration {
}
```

Essa classe registra a configuração de cache da biblioteca na aplicação. Não é necessário implementar novamente o `CacheManager`.

O `@EnableCaching` deve ser declarado na classe concreta, pois essa anotação não é herdada da classe-base `CacheConfiguration`.

**Atenção:** a classe deve ser detectada pelo component scan do Spring. Se estiver fora do escopo de varredura, ajuste o component scan ou registre a configuração por meio de `@Import`, conforme a organização da aplicação.

## 4. Utilizar o cache nas regras de negócio

A biblioteca disponibiliza duas anotações:

- `@CacheableResource`: consulta o cache `applicationCache`. Se não houver um resultado correspondente, executa o método e armazena seu retorno.
- `@CacheEvictResource`: remove todas as entradas do cache `applicationCache` após a execução bem-sucedida do método.

### Exemplo de consulta

Aplique `@CacheableResource` ao método responsável por consultar os dados.

```java
package br.com.banco.registration.service;

import br.com.banco.registration.infrastructure.cache.annotation.CacheableResource;
import br.com.banco.registration.repository.AgencyRepository;
import br.com.banco.registration.repository.entity.AgencyEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository repository;

    @CacheableResource
    public List<AgencyEntity> listAgencies() {
        return repository.findAll();
    }
}
```

**Comportamento:**

1. Na primeira chamada, o método consulta o banco de dados e o resultado é armazenado no cache.
2. Nas chamadas seguintes, enquanto a entrada estiver válida, o resultado é retornado do cache, sem executar novamente a consulta ao banco.
3. Quando a entrada expirar ou for removida, a próxima chamada executará o método e armazenará o novo resultado.

### Exemplo de atualização

Aplique `@CacheEvictResource` aos métodos que alteram os dados armazenados.

```java
package br.com.banco.registration.service;

import br.com.banco.registration.infrastructure.cache.annotation.CacheEvictResource;
import br.com.banco.registration.repository.AgencyRepository;
import br.com.banco.registration.repository.entity.AgencyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository repository;

    @CacheEvictResource
    public AgencyEntity register(AgencyEntity agency) {
        return repository.save(agency);
    }
}
```

Após o cadastro bem-sucedido, todas as entradas de `applicationCache` serão removidas. Na próxima consulta, o método será executado novamente e seu resultado será armazenado no cache.

> **Importante:** as anotações de cache do Spring são aplicadas por meio
> de proxies. A chamada ao método anotado deve ocorrer por meio do bean
> gerenciado pelo Spring. Uma chamada interna da mesma classe
> (autoinvocação) não passa pelo proxy e não aciona a anotação.

## 5. Resumo das anotações

Anotação Quando utilizar Comportamento

`@CacheableResource` Métodos de consulta. Retorna o valor do cache ou executa o método e armazena o resultado.

`@CacheEvictResource` Métodos que alteram os Remove todas as dados. entradas de `applicationCache` após a execução bem-sucedida.

## 6. Observações

- O nome do cache (`applicationCache`) é definido e centralizado pela biblioteca. Não precisa ser informado nas classes de negócio.
- A expiração automática é independente da invalidação realizada por `@CacheEvictResource`.
- A invalidação remove todas as entradas do cache, não apenas a entrada relacionada ao dado alterado.
- O Caffeine mantém o cache local à instância da aplicação. Em ambientes com múltiplas instâncias, cada instância mantém seu próprio cache.
- Os métodos anotados devem ser chamados por meio de beans gerenciados pelo Spring para que o mecanismo de cache seja aplicado.
- O cache utiliza a chave padrão do Spring, baseada nos argumentos do método. Métodos sem argumentos compartilham a mesma chave dentro do cache.
