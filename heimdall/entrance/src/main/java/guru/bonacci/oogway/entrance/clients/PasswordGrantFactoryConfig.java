package guru.bonacci.oogway.entrance.clients;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.entrance.security.Credentials;

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

    //Loadbalanced does not work for prototype scoped beans
	@Bean
	@Scope(value = SCOPE_PROTOTYPE)
	public RestTemplate restTemplate(Credentials credentials) {
		return new OAuth2RestTemplate(resourceDetails(credentials), new DefaultOAuth2ClientContext());
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
}

