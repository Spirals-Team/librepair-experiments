package guru.bonacci.oogway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.web.events.WebEventChannels;

/**
 * Service for the user to communicate with
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableBinding(WebEventChannels.class)
@IntegrationComponentScan
public class WebServer {

	public static void main(String[] args) {
		SpringApplication.run(WebServer.class, args);
	}
}
