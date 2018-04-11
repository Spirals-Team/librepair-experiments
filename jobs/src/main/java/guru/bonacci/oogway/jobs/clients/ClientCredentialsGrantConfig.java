package guru.bonacci.oogway.jobs.clients;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import feign.Logger;
import feign.RequestInterceptor;

@Configuration
@EnableCircuitBreaker
@Profile("!unit-test") // hack :)
public class ClientCredentialsGrantConfig {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	/**
	 * A few small things are needed to secure a service..
	 * 
	 * The command line can pretend to be jobs-service: 
	 * curl jobs-service:jobs-service-secret@localhost:5000/auth/oauth/token -d grant_type=client_credentials 
	 * curl -H "Authorization: Bearer f8f016c2-184c-432f-8ee6-6613e7dbfdfd" -v http://localhost:4444/oracle/gems/random
	 */
	@Bean
	public OAuth2RestTemplate clientCredentialsRestTemplate() {
		return new OAuth2RestTemplate(clientCredentialsResourceDetails());
	}

	/**
	 * Spring Boot (1.4.1) does not create an OAuth2ProtectedResourceDetails
	 * automatically if you are using client_credentials tokens. In that case you
	 * need to create your own ClientCredentialsResourceDetails and configure it
	 * with @ConfigurationProperties("security.oauth2.client").
	 */
	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
	}
}