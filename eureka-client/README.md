# Eureka Client

A simple eureka client that registers itself to the eureka server.

Connects to server to register and obtain information on other clients using their application's name.

This example stands up one instance within a random port and the web method returns your own location. 

**Config First Boostrap**

If a client uses a Spring Cloud Config Server and also uses a Spring Cloud Eureka Server it will first 
boostrap itself with the information on it from Spring Cloud Config. So we can put the _eureka.client.serviceUrl.defaultZone_ 
configuration into a config-server -instead of the client- where we can easily change the eureka-server location and all of 
the clients will find it.
<br>
In this example we will use this option.

**Eureka First Bootstrap**

When we use Eureka to expose location to config-server and the config-server is just another client. 
By this way client makes two network trips (first to eureka and then for config-server) to obtain 
configuration. Config-server will use _@EnableDiscoveryClient_ and eurica-client starter.
<br>
Use _spring.cloud.config.discovery.enabled=true_ and _eureka.client.serviceUrl.defaultZone_ configured in each app to retrive configs from config-server through eureka. Also remove bootstrap.yml and config-client starter.


**Build**

`$ ./mvnw clean install
`

**RUN**


`$ ./mvnw spring-boot:run
`

**TODO**

- get eureka.client.serviceUrl.defaultZone from config-server
- run multiple Eureka Servers (profile)
- instructions to test each profile
- instructions to running multiple instances of the same client (manual scalling)