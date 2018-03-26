package guru.bonacci.oogway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.oracle.client.OracleClientConfig;
import guru.bonacci.oogway.web.events.WebEventChannels;

/**
 * Service for the user to communicate with
 */
@SpringBootApplication
@EnableEurekaClient
@EnableBinding(WebEventChannels.class)
@IntegrationComponentScan
@Import(OracleClientConfig.class)
public class WebServer {

	public static void main(String[] args) {
		SpringApplication.run(WebServer.class, args);
	}
}
