package guru.bonacci.oogway.oracle.client;

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
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
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
		return new OAuth2RestTemplate(resourceDetails());
	}

//	ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
//	resource.setUsername("user1");
//	resource.setPassword("password");
//	resource.setAccessTokenUri("http://auth-service:5000/auth/oauth/token");
//	resource.setClientId("web-service");
//	resource.setClientSecret("web-service-secret");
//	resource.setGrantType("password");
//

//	@Bean
//    public OAuth2RestTemplate restTemplate() {
//		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
//		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(), clientContext);
//        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(), new DefaultOAuth2ClientContext(
//                new DefaultAccessTokenRequest()));
//        template.setAccessTokenProvider(userAccessTokenProvider());
//        return template;
//    }

//    @Bean
//    public AccessTokenProvider userAccessTokenProvider() {
//        ResourceOwnerPasswordAccessTokenProvider accessTokenProvider = new ResourceOwnerPasswordAccessTokenProvider();
//        return accessTokenProvider;
//    }
    
	@Bean
	public OAuth2ProtectedResourceDetails resourceDetails() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setUsername("user1");
		resource.setPassword("password");
		resource.setAccessTokenUri("http://auth-service:5000/auth/oauth/token");
		resource.setClientId("web-service");
		resource.setClientSecret("web-service-secret");
		resource.setGrantType("password");
		return resource;
	}

//	/**
//	 * Spring Boot (1.4.1) does not create an OAuth2ProtectedResourceDetails
//	 * automatically if you are using client_credentials tokens. In that case you
//	 * need to create your own ClientCredentialsResourceDetails and configure it
//	 * with @ConfigurationProperties("security.oauth2.client").
//	 */
//	@Bean
//	@ConfigurationProperties(prefix = "security.oauth2.client")
//	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
//		return new ClientCredentialsResourceDetails();
//	}

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resourceDetails());
	}
}	
