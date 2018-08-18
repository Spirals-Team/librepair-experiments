[![Build Status](https://travis-ci.org/moreiraMD/APIpayment.svg?branch=master)](https://travis-ci.org/moreiraMD/APIpayment)
# APIpayment
Simples serviço para fornecer uma API para pagamento aos nossos clientes e um check-out para quem não deseja integrar-se à nossa API.

# Rodando o projeto

Tenha na sua máquina as seguintes tecnologias:
- Docker
- Java 8
- Maven

Primeiro vamos subir nosso container para o banco de dados, um mysql:
```sh
$ docker run -d -p 3306:3306 --name=docker-mysql --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=moip" mysql
```
O segundo passo é dar um build no projeto e criarmos a imagem para o Docker, podemos fazer todos estes passos com o comando:
```sh
$ mvn clean install dockerfile:build
```
Já vamos ter os testes executados, build do projeto com todas suas dependências e nossa imagem para o terceiro passo, vamos rodar nosso container e criar um link com o do mysql:
```sh
docker run -d \
    --name api-payment \
    --link docker-mysql:mysql \
    -p 8080:8080 \
    -e DATABASE_HOST=docker-mysql \
    -e DATABASE_PORT=3306 \
    -e DATABASE_NAME=moip \
    -e DATABASE_USER=root \
    -e DATABASE_PASSWORD=root \
    apipayment
```

Com o projeto rodando é só acessar seu [LocalHost](http://localhost:8080/) para inserir um pagamento.
