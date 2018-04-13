package guru.bonacci.oogway.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import guru.bonacci.oogway.auth.services.MyUserService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

	private TokenStore tokenStore = new InMemoryTokenStore();

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserService();
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	//TODO .secret(env.getProperty("DOORWAY_SERVICE_PASSWORD"))

    	// @formatter:off
    	clients.inMemory()
	            .withClient("doorway-service")
	            .secret(passwordEncoder.encode("doorway-service-secret")) //there must be a better way..
				.authorizedGrantTypes("client_credentials", "password", "refresh_token")
	            .scopes("resource-server-read")
                .accessTokenValiditySeconds(1000)
                .refreshTokenValiditySeconds(30000)
		.and()
				.withClient("job-service")
				.secret(passwordEncoder.encode("job-service-secret")) //but it's already late.
				.authorizedGrantTypes("client_credentials", "refresh_token")
	            .scopes("resource-server-read", "resource-server-write");
		// @formatter:on
    }
}
