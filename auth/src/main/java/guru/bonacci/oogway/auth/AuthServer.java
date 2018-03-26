package guru.bonacci.oogway.auth;

import static org.slf4j.LoggerFactory.getLogger;

import java.security.Principal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableResourceServer
public class AuthServer {

	private final Logger logger = getLogger(this.getClass());

	public static void main(String[] args) {
        SpringApplication.run(AuthServer.class, args);
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        logger.info("AS /user has been called");
        logger.debug("user info: " + user.toString());
        return user;
    }
    
    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        	//TODO .secret(env.getProperty("WEB_SERVICE_PASSWORD"))
        	// 		@Autowired private Environment env;

			// @formatter:off
        	clients.inMemory()
		            .withClient("web-service")
		            .secret("web-service-secret")
					.authorizedGrantTypes("client_credentials", "refresh_token")
		            .scopes("resource-server-read", "resource-server-write")
			.and()
					.withClient("job-service")
					.secret("job-service-secret")
					.authorizedGrantTypes("client_credentials", "refresh_token")
		            .scopes("resource-server-read", "resource-server-write");
			// @formatter:on
        }
    }
}
