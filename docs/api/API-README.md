# API README

### Atualização do contrato OpenAPI

Este contrato precisa sempre estar atualizado no diretório `docs/api/`, então siga os passos abaixo:

Abra um terminal e execute o comando abaixo na raiz do projeto

```bash
mvn spring-boot:run
```

Abra um novo terminal e execute o comando abaixo na raiz do projeto

```bash
curl.exe http://localhost:8080/v3/api-docs.yaml -o docs/api/openapi.yaml
```


- README.md: explica como acessar o Swagger e atualizar o contrato.
- openapi.yaml: cópia versionada do contrato gerado.
