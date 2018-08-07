package guru.bonacci.oogway.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OrchestrationServer {

	public static void main(String[] args) {
		SpringApplication.run(OrchestrationServer.class, args);
	}
}
