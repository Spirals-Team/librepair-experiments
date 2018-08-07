package guru.bonacci.oogway.doorway.clients;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import feign.RequestInterceptor;

@Configuration
@Profile("!unit-test") // hack :)
public class ClientCredentialsGrantConfig {

	@Primary
	@Bean
	@LoadBalanced
	public RestTemplate clientCredentialsRestTemplate() {
		OAuth2RestTemplate template = new OAuth2RestTemplate(clientCredentialsResourceDetails(), oAuth2ClientContext());
		template.setAccessTokenProvider(accessTokenProvider());
		return template;
	}

	@Bean
	OAuth2ClientContext oAuth2ClientContext() {
		return new DefaultOAuth2ClientContext();
	}

	@Bean
	ClientCredentialsAccessTokenProvider accessTokenProvider() {
		return new CustomClientCredentialsAccessTokenProvider(loadBalancedTemplate());
	}

	@LoadBalanced
	@Bean
	RestTemplate loadBalancedTemplate() {
		return new RestTemplate();
	}

	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	OAuth2ProtectedResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		OAuth2FeignRequestInterceptor interceptor = new OAuth2FeignRequestInterceptor(oAuth2ClientContext(), clientCredentialsResourceDetails());
		interceptor.setAccessTokenProvider(accessTokenProvider());
		return interceptor;
	}
	
	// Allows us to set a (loadbalanced) resttemplate
	static class CustomClientCredentialsAccessTokenProvider extends ClientCredentialsAccessTokenProvider {

		private RestOperations restOperations;

		public CustomClientCredentialsAccessTokenProvider(RestOperations restOperations) {
			this.restOperations = restOperations;
		}

		@Override
		protected RestOperations getRestTemplate() {
			setMessageConverters(new RestTemplate().getMessageConverters());
			return this.restOperations;
		}
	}
}