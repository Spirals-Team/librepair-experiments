# Ribbon Client

Ribbon is a client side load balancer with the following features:

- Automatically integrates with Eureka.
- Built in failure resiliency (Hystrix).
- Caching/Batching.
- Multiple protocols (HTTP, TCP, UDP).
- Default Round Robin strategy to load balance.

**Build**

`$ ./mvnw clean install
`

**RUN**


`$ ./mvnw spring-boot:run
`

**TODO**

- Exaplaning class controller and Instructions to test it: 
1. run multiples eureka-clients 
2. run ribbon
3. demonstrate getting information (ip:port) from multiple eureka-client using the ribbon-client as a load balancer

`curl http://192.168.0.19:8020/eureka-client-service-url/eureka-client
`
`curl http://192.168.0.19:8020/eureka-client-service-url/eureka-client
`
`curl http://192.168.0.19:8020/eureka-client-service-url/eureka-client
`

The host will changes with multple calls.

4. Eureka dashboard image.
5. Refactor with feign.