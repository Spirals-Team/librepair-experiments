package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;

@Configuration
public class TokenServiceConfiguration {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ClientDetailsServiceImpl clientDetailsService;

  @Autowired
  private TokenStore tokenStore;

  @Bean
  public DefaultTokenServices tokenServiceBean() {
    DefaultTokenServices tokenService = new DefaultTokenServices();
    tokenService.setAuthenticationManager(authenticationManager);
    tokenService.setClientDetailsService(clientDetailsService);
    tokenService.setReuseRefreshToken(false);
    tokenService.setTokenStore(tokenStore);
    return tokenService;
  }
}