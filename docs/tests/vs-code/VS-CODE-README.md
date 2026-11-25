# VS-CODE README


- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml


## Instalação do plugin

Instale a extensão `REST Client` no VS Code através do comando abaixo no terminal.

```bash
code --install-extension humao.rest-client
```

## Como Realizar os testes
Abra o arquivo


### Testes com REST Client (Opcional)

É possível testar os endpoints da aplicação diretamente pelo VS Code utilizando a extensão [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client).

O uso dessa extensão é totalmente opcional. Ela serve apenas como uma alternativa ao Postman ou ao cURL para executar e validar as requisições HTTP.

#### Como utilizar

1. Abra o arquivo [resources/vs-code/registration.http](src/main/resources/vs-code/registration.http)
2. Acima do verbo da requisição irá aparecer `Send Request`, Execute as requisições clicando nele.



O projeto pode ser testado usando o plugin `REST Client` do vs-code.
Acesse o arquivo [docs/tests/vs-code/API-README.md](docs/tests/vs-code/API-README.md) para mais informações.
