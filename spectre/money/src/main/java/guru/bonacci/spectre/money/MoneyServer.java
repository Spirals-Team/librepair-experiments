package guru.bonacci.spectre.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.spectre.spectreshared.events.SpectreEventChannels;
import guru.bonacci.spectre.spectreshared.persistence.PersistenceConfig;

@SpringBootApplication
@EnableBinding(SpectreEventChannels.class)
@Import(PersistenceConfig.class)
public class MoneyServer {
	
	public static void main(String[] args) {
		SpringApplication.run(MoneyServer.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
