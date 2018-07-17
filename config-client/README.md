# Spring Cloud Config Client

A simple client that retrivies a message from config-server based on his profile.

**RUN**

`$ ./mvnw clean spring-boot:run`


**E.g.**

**Retrive a message based on DEFAULT profile:**<br>
`$ ./mvnw clean spring-boot:run
`

```
$ curl http://localhost:8080/message
hello world
```

**Retrive message based on DEV profile:**<br>
`$ ./mvnw clean spring-boot:run -Dspring.profiles.active=dev
`

```
$ curl http://localhost:8080/message
hello from dev profile
```

**Retrive message based on QA profile:**<br>
`$ ./mvnw clean spring-boot:run g.profiles.active=qa
`

```
$ curl http://localhost:8080/message
hello hello from qa profile
```

**Retrive message based on PROD profile**:<br>
`$ ./mvnw clean spring-boot:run -Dspring.profiles.active=prod
`

```
$ curl http://localhost:8080/message
hello from prod profile
```

**Refresh Client** <br>

Refresh config-client after commit changes on config-server:

`$ curl localhost:8080/actuator/refresh -d {} -H "Content-Type: application/json"
`