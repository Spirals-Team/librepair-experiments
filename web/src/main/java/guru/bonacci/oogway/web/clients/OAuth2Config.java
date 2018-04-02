package guru.bonacci.oogway.web.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

@RefreshScope
@Configuration
public class OAuth2Config {

	public final static String OAUTH2_TEMPLATE_BEAN = "oAuth2RestTemplate";

    @Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;
    
    @Value("${security.oauth2.client.grant-type}")
	private String grantType;
    
	@RefreshScope
	@Bean(OAUTH2_TEMPLATE_BEAN)
	@Scope("prototype")
	OAuth2RestTemplate restTemplate(@Value("${u:user1}") String username,
								    @Value("${pw:password}") String pw) {
		return new OAuth2RestTemplate(resourceDetails(pw, username), new DefaultOAuth2ClientContext());
	}

	@RefreshScope
	@Bean
	@Scope("prototype")
	ResourceOwnerPasswordResourceDetails resourceDetails(@Value("${u:user1}") String username,
														 @Value("${pw:password}") String pw) {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setUsername(username);
		resource.setPassword(pw);
		resource.setAccessTokenUri(accessTokenUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setGrantType(grantType);
		return resource;
	}	
}

