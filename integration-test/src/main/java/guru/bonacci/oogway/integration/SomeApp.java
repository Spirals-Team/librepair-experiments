package guru.bonacci.oogway.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SomeApp {

    public static void main(String[] args) {
        SpringApplication.run(SomeApp.class, args);
    }
}