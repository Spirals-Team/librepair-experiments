package guru.bonacci.oogway.lumberjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import guru.bonacci.oogway.lumberjack.persistence.Log;
import guru.bonacci.oogway.lumberjack.persistence.LogService;

@EnableMongoRepositories
@EnableEurekaClient
@SpringBootApplication
public class LumberjackServer {
	
	public static void main(String[] args) {
		SpringApplication.run(LumberjackServer.class, args);
	}

	@Bean
	public AbstractMongoEventListener<Log> mongoEventListener(LogService service) {
	    return service.new LogPersistListener();
	}
}
