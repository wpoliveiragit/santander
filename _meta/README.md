# Organização de arquivos auxiliares

Este diretório centraliza documentos e recursos auxiliares do projeto que não fazem parte diretamente do código-fonte da aplicação.

A organização é dividida em duas áreas principais: documentação e ferramentas de suporte ao desenvolvimento.

## Estrutura

```
_meta/
├── docs/
│   ├── api/
│   ├── references/
│   ├── architecture/
│   ├── database/
│   └── tests/
│
└── tooling/
    ├── environments/
    │   └── local-dev/
    │       └── .env
    │
    ├── tests/
    │   └── vs-code/
    │       └── registration.http
    │
    └── README.md
```

## `docs/`

Centraliza a documentação funcional, técnica e arquitetural do projeto.

### `requirements/`

Documentação dos requisitos funcionais e não funcionais, regras de negócio, escopo e comportamento esperado da aplicação.

### `architecture/`

Documentação sobre a arquitetura do microsserviço, organização interna, componentes, decisões arquiteturais, integrações e diagramas.

### `api/`

Documentação relacionada à API, incluindo especificações OpenAPI, endpoints, contratos de comunicação, parâmetros e modelos de requisição e resposta.

### `database/`

Documentação relacionada à persistência de dados, incluindo modelo de dados, relacionamentos, estrutura das tabelas, scripts e decisões sobre armazenamento.

### `references/`

Materiais de referência utilizados como apoio ao desenvolvimento, incluindo especificações originais, materiais de estudo, documentações externas e outras fontes relevantes.

## `tooling/`

Centraliza ferramentas, configurações e recursos auxiliares utilizados durante o desenvolvimento, construção, testes e operação do microsserviço.

### `environments/`

Armazena arquivos relacionados às configurações dos ambientes utilizados pelo projeto.

#### `local-dev/`

Contém recursos exclusivos do ambiente de desenvolvimento local.

- `.env`: variáveis de ambiente utilizadas durante a execução local da aplicação.

Futuramente, poderão ser adicionadas configurações específicas de outros ambientes, conforme a necessidade do projeto.

### Evolução do diretório

O `tooling/` poderá receber outras categorias de recursos conforme o projeto evoluir, por exemplo:

- `containers/`: Dockerfiles, Docker Compose e configurações de containers.
- `database/`: scripts auxiliares para inicialização e manutenção de bancos de dados locais.
- `automation/`: scripts para automatizar tarefas de desenvolvimento e manutenção.
- `ci-cd/`: configurações e recursos relacionados aos processos de integração e entrega contínua.
- `deployment/`: artefatos e configurações auxiliares de implantação.

Esses diretórios serão adicionados somente quando houver necessidade real.

## Diretrizes de organização

- Manter documentação e ferramentas auxiliares separadas do código-fonte.
- Organizar os arquivos por responsabilidade e contexto.
- Agrupar recursos em `_meta/` sempre que sua localização não for uma exigência técnica de determinada ferramenta.
- Preservar os caminhos convencionais quando necessários para compatibilidade e funcionamento correto das ferramentas.
- Não incluir no artefato final arquivos que não sejam necessários à execução da aplicação.
- Evitar estruturas antecipadas e diretórios sem utilização.
- Manter a documentação próxima do contexto ao qual pertence.

Esta organização busca manter a raiz do projeto limpa, facilitar sua manutenção e permitir a evolução independente da documentação e dos recursos de engenharia do microsserviço.
