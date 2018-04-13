package guru.bonacci.oogway.doorway.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

@Configuration
@Profile("!unit-test") // hack :)
public class CredentialsConfig {
	
	@Bean
	Decoder decoder() {
		return new CredentialsDecoder();
	}
	
	@Bean
	ErrorDecoder errorDecoder() {
		return new CredentialsErrorDecoder();
	}
}