package guru.bonacci.spectre.localtimer;

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
public class LocalTimerServer {
	
	public static void main(String[] args) {
		SpringApplication.run(LocalTimerServer.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
