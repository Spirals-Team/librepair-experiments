# Feign Client

A declarative REST client from NetFlix

- Allows to write calls to REST services with no implementation code by using interfaces that will implemented at run-time.
- Alternative to RestTemplate or Apache HttpClient.
- Annotate with Spring MVC annotations that describe how the service is defined on the server side.
- Easy integration with Eureka using eureka client-id.
- Ribbon is automatically enabled and is going to automatically load balance between the clients in the same pool.

By steps...

1. Eureka gives our application all "Clients" that match the given Client ID.
2. Ribbon automatically applies load balancing.
3. Feign handles the code.


**Build**

`$ ./mvnw clean install
`

**RUN**


`$ ./mvnw spring-boot:run
`

**TODO**

- Exaplaning class controller and Instructions to test it: 
1. run multiples eureka-clients 
2. run feign-client
3. demonstrate getting information (ip:port) from multiple eureka-client using the feign-client

`curl http://192.168.0.19:8020/eureka-client-repository-url/eureka-client
`
`curl http://192.168.0.19:8020/eureka-client-repository-url/eureka-client
`
`curl http://192.168.0.19:8020/eureka-client-repository-url/eureka-client
`

The host will changes with multple calls.

4. Eureka dashboard image.