package guru.bonacci.oogway.oracle.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import feign.RequestInterceptor;

@Configuration
@ComponentScan
@EnableFeignClients(basePackageClasses = OracleClient.class)
@EnableCircuitBreaker
@PropertySource("classpath:oracle-client.yml")
@EnableOAuth2Client
@Profile("!unit-test") //hack :)
public class OracleClientConfig {
	
	/**
	 * A few small things are needed to secure a service..
	 * 
	 * The command line can pretend to be web-service:
	 * curl web-service:web-service-secret@localhost:5000/auth/oauth/token -d grant_type=client_credentials
	 * curl -H "Authorization: Bearer f8f016c2-184c-432f-8ee6-6613e7dbfdfd" -v http://localhost:4444/oracle/gems?q=abc
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
