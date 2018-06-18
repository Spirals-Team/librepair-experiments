# abc academia

Esse projeto é composto de 3 partes:

- um banco de dados (`academia-db`)
- uma API (em Java) que se conecta nesse banco e provê uma JSON API com os dados do sistema (`academia-api`)
- um cliente web (em React) que se conecta na API e provê as funcionalidades ao usuário (`academia-client-web`)


## desenvolvendo

Sugerimos rodar o banco de dados em Docker, sempre.

Se você for desenvolver a `academia-api`, sugerimos que você rode o `academia-db` em Docker, e rode a `academia-api` com Docker ou sem, à sua escolha. Não é necessário rodar o `academia-client-web` para desenvolver a API (mas pode ser útil, se você preferir).

Se você for desenvolver a `academia-client-web`, sugerimos que você rode o `academia-db` em Docker, a `academia-api` com Docker e o `academia-client-web` com Docker ou sem, à sua escolha. Veja detalhes de como rodar com Docker no README do `academia-client-web`.

## rodando

Optamos por executar as partes da nossa aplicação usando Docker. Existem 2 formas para rodar.

### docker compose

A forma mais simples de rodar é com docker-compose, rodando:

```shell
./scripts/docker-compose-build.sh # apenas uma vez, ou quando houver alteração
./scripts/docker-compose-up.sh
```

Essa forma não é recomendável pa

### docker puro
Se você for rodar o sistema com Docker, é necessário primeiro criar a rede virtual do Docker, rodando o script:

```shell
./scripts/docker-network.sh
```

ou rodando o conteúdo dele diretamente

Cada parte da aplicação contém seu README com mais informações de como rodar, mas basicamente:

- rode o banco:

  ```shell
  cd academia-db
  ./server.sh
  ```

- rode a API:

  ```shell
  cd academia-api
  ./scripts/docker-run.sh
  ```

- rode o cliente web:
  ```shell
  cd academia-client-web
  ./scripts/docker-run-dev.sh # se você for desenvolver e quiser ver as alterações sendo refletidas na aplicação
  # ou
  ./scripts/docker-run-prod.sh # se você só quiser rodar
  ```
