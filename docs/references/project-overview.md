# Desafio – API de Agências Bancárias

## Objetivo

Desenvolver uma API REST utilizando **Java, Spring Boot e OAuth2** para cadastrar agências bancárias e consultar as distâncias entre a posição de um usuário e as agências cadastradas.

As agências devem possuir coordenadas cartesianas `(X, Y)` e seus dados devem ser persistidos em um banco de dados.

Para otimizar as consultas, a aplicação deverá utilizar um mecanismo de **cache**, evitando acessos desnecessários ao banco de dados.

Quando o cache expirar ou for invalidado, os dados deverão ser recuperados diretamente do banco de dados e armazenados novamente no cache.

## Requisitos funcionais

1. Disponibilizar um endpoint para cadastrar agências com coordenadas X e Y.
2. Disponibilizar um endpoint para consultar a distância entre o usuário e todas as agências cadastradas.
3. Ordenar os resultados da consulta da menor para a maior distância.
4. Persistir as agências cadastradas em um banco de dados.
5. Exigir autenticação para realizar cadastros e consultas.

## Cadastro de agências

Disponibilizar um endpoint `POST` no seguinte caminho:

`/desafio/cadastrar`

**Exemplo de URL:**

`http://localhost:8080/desafio/cadastrar`

O endpoint deverá receber, no corpo da requisição (`Body`), um JSON contendo as coordenadas X e Y da agência.

**Exemplo de requisição:**

```json
{
  "posX": 10,
  "posY": -5
}
```

**Regras:**

- As agências cadastradas deverão ser persistidas em um banco de dados.
- O endpoint deverá exigir autenticação.
- Os dados cadastrados deverão ser considerados nas próximas consultas de distância.

## Consulta de distância entre usuário e agências

Disponibilizar um endpoint `GET` no seguinte caminho:

`/desafio/distancia`

O endpoint deverá receber as coordenadas X e Y do usuário por meio de parâmetros de consulta (`Query Parameters`).

**Exemplo de URL:**

`http://localhost:8080/desafio/distancia?posX=-10&posY=5`

A aplicação deverá calcular a distância em linha reta entre a posição informada pelo usuário e cada uma das agências cadastradas.

O cálculo deverá utilizar a fórmula da distância euclidiana:

\[
d = \sqrt{(x_2-x_1)^2+(y_2-y_1)^2}
\]

**Regras:**

- O endpoint deverá exigir autenticação.
- Os resultados deverão ser retornados em formato JSON.
- As agências deverão ser ordenadas da menor para a maior distância.
- A consulta deverá utilizar os dados armazenados em cache, quando disponíveis.
- Caso o cache esteja expirado ou tenha sido invalidado, os dados deverão ser recuperados do banco de dados e armazenados novamente no cache.

**Exemplo de retorno:**

```json
[
  {
    "agencia": "AGENCIA_2",
    "distancia": 2.2
  },
  {
    "agencia": "AGENCIA_1",
    "distancia": 10.0
  },
  {
    "agencia": "AGENCIA_3",
    "distancia": 37.42
  }
]
```

## Requisitos de cache

A aplicação deverá implementar um mecanismo de cache para armazenar as agências cadastradas e otimizar as consultas.

Comportamento esperado:

- O cache deverá expirar automaticamente após 10 minutos.
- O cache também deverá ser invalidado após 5 minutos sem consultas ao endpoint de distância.
- Após a invalidação, a próxima consulta deverá recuperar os dados do banco de dados e reconstruir o cache.

## Exemplo prático

Considere as seguintes agências cadastradas:

| Agência   | Coordenadas (X, Y) |
| --------- | ------------------ |
| AGENCIA_1 | (2, 2)             |
| AGENCIA_2 | (10, 4)            |
| AGENCIA_3 | (5, 2)             |
| AGENCIA_4 | (10, 7)            |

**Coordenadas do usuário:**

`(6, 5)`

**Retorno esperado (ordenado pela menor distância):**

| Ordem | Agência   | Coordenadas | Distância |
| ----: | --------- | ----------- | --------: |
|     1 | AGENCIA_3 | (5, 2)      |      3,16 |
|     2 | AGENCIA_2 | (10, 4)     |      4,12 |
|     3 | AGENCIA_4 | (10, 7)     |      4,47 |
|     4 | AGENCIA_1 | (2, 2)      |      5,00 |

O retorno deverá conter todas as agências cadastradas, respeitando a ordenação crescente das distâncias calculadas.
