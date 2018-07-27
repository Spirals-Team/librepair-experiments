[![Build Status](https://travis-ci.org/lucascoelhosilva/projectspring.svg?branch=master)](https://travis-ci.org/lucascoelhosilva/projectspring)

# Project Spring Boot

    mvn package

# Docker Compose
	docker-compose build
    docker-compose up
    

#####API do sistema de ponto inteligente com Java e Spring Boot.

Detalhes da API RESTful
A API RESTful de Ponto Inteligente contém as seguintes características:

Projeto criado com:
 - Spring Boot e Java 8

Banco de dados:
 - POSTGRES com JPA e Spring Data JPA

Autenticação e autorização:
 - Spring Security e tokens JWT (JSON Web Token)

Migração de banco de dados: 
 - Flyway

Testes unitários e de integração: 
 - JUnit e Mockito

Caching: 
 - EhCache
 
Integração contínua: 
 - TravisCI


####Como executar a aplicação:

Importe o projeto como projeto Maven no Eclipse ou STS

No terminal, execute a seguinte operação:
    
    mvn eclipse:eclipse
    
    mvn spring-boot:run

Acesse os endpoints através da url http://localhost:8080