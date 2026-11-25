# Santander- Desafio OSA

[Link do desafio](src/main/resources/doc/desafio.md)

**Observação:** O projeto possui apenas o que foi solicitado e contendo convenções de programação

- [] dasdas

## Endpoints

### Geração de token

```bash
curl --request POST \
  --url 'http://localhost:8080/oauth/token?username=user'
```

### Cadastro de agência

```bash
curl --request POST \
  --url http://localhost:8080/desafio/cadastrar \
  --header 'Authorization: Bearer SEU_TOKEN_JWT' \
  --header 'Content-Type: application/json' \
  --data '{
  "posX": 12.5,
  "posY": 50.5
}'
```

### Obtem distâncias de cada agencia cadastrada

```bash
curl --request GET \
  --url 'http://localhost:8080/desafio/distancia?posX=10&posY=20' \
  --header 'Authorization: Bearer SEU_TOKEN_JWT'
```
