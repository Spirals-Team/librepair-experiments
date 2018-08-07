package guru.bonacci.oogway.doorway.clients;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.doorway.security.Credentials;

@RefreshScope
@Configuration
@Profile("!unit-test") //hack :)
public class PasswordGrantFactoryConfig {

    @Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;

    @Autowired
    public SpringClientFactory clientFactory;
	
	@Autowired
    public LoadBalancerClient loadBalancer;

	@Bean
    @LoadBalanced
	@Scope(value = SCOPE_PROTOTYPE)
	public RestTemplate restTemplate(Credentials credentials) {
		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails(credentials), new DefaultOAuth2ClientContext());
		template.setAccessTokenProvider(accessTokenProvider());
    	template.setRequestFactory(new CustomRibbonClientHttpRequestFactory(clientFactory, loadBalancer));
		return template;
	}

	@Bean
	@Scope(value = SCOPE_PROTOTYPE)
	OAuth2ProtectedResourceDetails resourceDetails(Credentials credentials) {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setUsername(credentials.getUsername());
		resource.setPassword(credentials.getPassword());
		return resource;
	}	

	// Sssst, don't tell spring...
	private ResourceOwnerPasswordAccessTokenProvider accessTokenProvider() {
		return new CustomResourceOwnerPasswordAccessTokenProvider(loadBalancedTemplate());
	}

	@LoadBalanced
	@Bean
	RestTemplate loadBalancedTemplate() {
		return new RestTemplate();
	}

	// Allows us to set a (loadbalanced) resttemplate
	static class CustomResourceOwnerPasswordAccessTokenProvider extends ResourceOwnerPasswordAccessTokenProvider {

		private RestOperations restOperations;

		public CustomResourceOwnerPasswordAccessTokenProvider(RestOperations restOperations) {
			this.restOperations = restOperations;
		}

		@Override
		protected RestOperations getRestTemplate() {
			setMessageConverters(new RestTemplate().getMessageConverters());
			return this.restOperations;
		}
	}
}

