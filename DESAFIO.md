# Santander - Desafio OSA!

`Contexto:` Criação de uma API REST em Spring Boot & OAuth2 que realiza cadastramentos de Agências, sua posição X & Y,
onde o usuário ao realizar uma consulta deverá retornar as distâncias das agências cadastradas pegos de um Cache ou caso
o tempo do `Cache` expire retorno diretamente do banco e recria um novo cache.
---

1. Criação de um ENDPOINT de cadastramento de uma AGÊNCIA em coordenadas X e Y.
1. Criação de um ENDPOINT de pesquisa da DISTÂNCIA entre o USUÁRIO e as AGÊNCIAS CADASTRADAS.

## CADASTRAMENTO DE AGÊNCIAS:

Para cadastrar uma agência deve ser criado um `POST` com contexto de `/desafio/cadastrar`.

**Exemplo URL:** `localhost:8080/desafio/cadastrar`

Passando em seu `BODY` um JSON, com a `posição(X, Y)` da agência.

Exemplo: Body (JSON):

```json
{
  "posX": 10,
  "posY": -5
}
```

- As agências cadastradas devem ser colocadas em um banco de dados.
- O cadastramento e a consulta deverão passar por autenticação.
- O Cache deverá ser renovado a cada 10 automaticamente ou após 5 minutos de uma consulta (ENDPOINT 2).

## USUÁRIO E DISTÂNCIA ENTRE A AGÊNCIA:
Na procura de agência mais próxima do Usuário deve ser criado um `GET` com contexto de `/desafio/distancia/`, deverá
receber como `parâmetro de URL` a `posição(X, Y)` do USUÁRIO.

Exemplo URL: `localhost:8080/desafio/distancia?posX=-10&posY=5`

Ao ser executado o GET deve ser retornado a `DISTÂNCIA` entre o `USUÁRIO` e as `AGÊNCIAS` cadastradas em um `JSON`.

**O retorno deve ser ordenado pela distância mais próxima para a mais distante.**

Exemplo:

```json
{
  "AGENCIA_2": "distância = 2.2",
  "AGENCIA_1": "distância = 10",
  "AGENCIA_3": "distância = 37.42"
}
```