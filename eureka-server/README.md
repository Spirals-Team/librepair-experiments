# Eureka Server

Service Discovery With Spring Cloud Eureka

When we use microservices we typically habe a large number of services that need to be interrelated. They need to call each other and a Service Discovery provides a single 'lookup' service where cliets can register themselves and discover other registrants.

**How it works**

- Eureka generally made highly available by running multiple copies (3 minimum) and each copy replicate state of registered services.
- When client services register with Eureka Server they provide metadata on host, port and health indicator URL.
- Clients will send heartbeats to Eureka and Eureka will removes services without heartbeats.

See:
https://github.com/Netflix/eureka/wiki/Configuring-Eureka

**Build**

`$ ./mvnw clean install
`

**Run as a single instance**


`$ ./mvnw spring-boot:run
`

**Run 3 intercommunicating instances**

`$ ./mvnw clean spring-boot:run -Dspring.profiles.active=us-east-1
`

`$ ./mvnw clean spring-boot:run -Dspring.profiles.active=us-west-1
`

`$ ./mvnw clean spring-boot:run -Dspring.profiles.active=ap-south-1
`

**Eureka Dashboard from default instance**

http://localhost:8010/


**TODO**

- shell script to run the e.g. above