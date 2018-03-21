package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.web.utils.LoggingExceptionTranslator;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ClientDetailsServiceImpl clientDetailsService;

  @Autowired
  private DefaultTokenServices tokenService;

  @Autowired
  private LoggingExceptionTranslator loggingExceptionTranslator;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager).exceptionTranslator(loggingExceptionTranslator)
        .reuseRefreshTokens(false).tokenServices(tokenService).tokenStore(tokenStore)
        .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }
}