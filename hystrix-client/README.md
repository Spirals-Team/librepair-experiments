# Hystrix Client

Circuit Breaker with Netflix Hystrix

- Fallbacks to prevents cascading failures.
- Monitoring with the Hystrix Dashboard and Turbine.

**Build**

`$ ./mvnw clean install
`

**RUN**


`$ ./mvnw spring-boot:run
`

**Hystrix Dashboard**

On: 

http://localhost:8040/hystrix

Put: 

http://localhost:8040/actuator/hystrix.stream into the input text...
 
and click to:
 
_Monitor System_



**TODO**

- Instructions to test (run eureka clients to close circuit and stop to open circuits)
- Turbine