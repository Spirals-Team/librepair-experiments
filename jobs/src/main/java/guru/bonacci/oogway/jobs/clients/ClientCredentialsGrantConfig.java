package guru.bonacci.oogway.jobs.clients;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import feign.RequestInterceptor;

@Configuration
@EnableCircuitBreaker
@Profile("!unit-test") // hack :)
public class ClientCredentialsGrantConfig {

	/**
	 * A few small things are needed to secure a service..
	 * 
	 * The command line can pretend to be jobs-service: 
	 * curl jobs-service:jobs-service-secret@localhost:5000/auth/oauth/token -d "grant_type=client_credentials" 
	 * curl -H "Authorization: Bearer f8f016c2-184c-432f-8ee6-6613e7dbfdfd" -v http://localhost:4444/oracle/gems/random
	 */
	@Primary
	@LoadBalanced
	RestTemplate clientCredentialsRestTemplate() {
		OAuth2RestTemplate template = new OAuth2RestTemplate(clientCredentialsResourceDetails(), oAuth2ClientContext());
		template.setAccessTokenProvider(accessTokenProvider());
		return template;
	}

	
	@Bean
	OAuth2ClientContext oAuth2ClientContext() {
		return new DefaultOAuth2ClientContext();
	}

	@Bean
	AccessTokenProvider accessTokenProvider() {
		return new MyClientCredentialsAccessTokenProvider(loadBalancedTemplate());
	}

	@LoadBalanced
	@Bean
	RestTemplate loadBalancedTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	RequestInterceptor oauth2FeignRequestInterceptor() {
		OAuth2FeignRequestInterceptor interceptor = new OAuth2FeignRequestInterceptor(oAuth2ClientContext(), clientCredentialsResourceDetails());
		interceptor.setAccessTokenProvider(accessTokenProvider());
		return interceptor;
	}
	
	// Allows us to set a (loadbalanced) resttemplate
	static class MyClientCredentialsAccessTokenProvider extends ClientCredentialsAccessTokenProvider {

		private RestOperations restOperations;

		public MyClientCredentialsAccessTokenProvider(RestOperations restOperations) {
			this.restOperations = restOperations;
		}

		@Override
		protected RestOperations getRestTemplate() {
			setMessageConverters(new RestTemplate().getMessageConverters());
			return this.restOperations;
		}
	}
}