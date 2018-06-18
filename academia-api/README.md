# academia-api

API de acesso a dados e serviços da academia.

## para desenvolvimento

Indicado rodar pela sua IDE de preferência.


## rodando localmente

Se você não quiser rodar com Docker, pode rodar diretamente no seu SO.

### pré-requisitos

É necessário ter Java 8 (JDK) instalado na máquina.

### instalando dependências

```
make build
```

ou se você não tiver `make`:

```
mvn clean install
```

### rodando a app

```
make run # dispensa a execução de `make build`
```

ou se você não tiver `make`:

```
java -jar target/academia-api-1.0-SNAPSHOT.jar
```

A aplicação irá subir no endereço [http://localhost:8080]()


## rodando com docker

Só exige Docker instalado na máquina.

Rode o script (a partir da raíz do projeto `academia-api`):

```
# se o script for incompatível com seu SO, copie os comandos do script e os execute diretamente
./scripts/docker-run.sh
```

A aplicação irá subir no endereço [http://localhost:8080]()


## usando `curl` para chamar a API

```bash
curl -u <usuário>:<senha> localhost:8080/api/accounts
```
