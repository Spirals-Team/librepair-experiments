# Phonebook 

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/297a8adb85224fc7a6dc2b8fdf2c5232)](https://www.codacy.com/app/evgeniy/Phonebook)

[![Build Status](https://travis-ci.org/roldevg/phonebook.svg?branch=master)](https://travis-ci.org/roldevg/phonebook)

## Tools

- Backend: 

JDK 8, Spring Boot 1.x (Core, MVC, Data, Actuator, Security),
JPA/Hibernate 5, JUnit 5, Mockito, Maven, Tomcat 8, MySql/H2, Lombok, 
Swagger, Flyway, Docker, Checkstyle, Logging

- Frontend:

JSP, Bootstrap, jQuery
  
## Screenshots

Index: 

![Image](https://i.gyazo.com/1a933394d633716e6149808cb1a3ae95.png)

Profile: 

![Image](https://i.gyazo.com/c25878f30dd2d5bde4decc5cd48f8d88.png)

## Functionality

+ login/logout/remember me
+ ajax search of employees 
+ display profile employee 
+ edit profile employee 
+ add/delete a profile
+ upload and download avatar

## Default Users

- login: admin
- pass: admin

## Docker

- Build:

docker build -t phonebook:demo .

- Run:

docker run -p 8080:8080 phonebook:demo

- Stop:

docker stop {container_id}

## Local build

mvn clean install

## Local run 

mvn spring-boot:run -f ./webpp-ui

## Swagger UI Urls

- REST Documentation

http://host:8080/swagger-ui.html

- Api Docs End Point

http://host:8080/v2/api-docs

### Demo on the Heroku

https://web-phonebook.herokuapp.com
