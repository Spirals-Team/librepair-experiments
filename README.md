# Spring Cloud Netflix Playground

[![Build Status](https://travis-ci.org/fpjunqueira/spring-cloud-netflix-playground.svg?branch=master)](https://travis-ci.org/fpjunqueira/spring-cloud-netflix-playground)

_Just playing around microservices based on spring-cloud-netflix_

The intention of this project is to provide with a help of Spring Cloud Netflix a basic configuration of the main communication patterns between micro-services such as API gateway (Zull), service discovery (Eureka), circuit breaker (Hystrix) and load balancer (Ribbon), as well as a configuration server module (Spring Cloud Config) and another module for authentication based on OAuth2.

**Build Projects**

`$ ./mvnw clean install
`

**TODO Modules**

- Zuul
- Swagger
- Server A
- Server B
- Oauth2 
- Sleuth
- Zipkin
- Turbine
- Spring Boot Admin

**TODO Readme**

1. Server A
2. Server B
3. Register both servers in eureka
4. Intercomunicate with feign calls
5. Enable Ribbon Client
6. Enable Hystrix
7. Config Zull Gateway
8. Config swagger
9. Eureka dashboard Image/link
10. Zull dashboard Image/link
11. Swagger Image/link