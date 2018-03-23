package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ch.tbmelabs.tv.core.authorizationserver.configuration.OAuth2AuthorizationServerConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;
import ch.tbmelabs.tv.core.authorizationserver.web.utils.LoggingExceptionTranslator;

public class OAuth2AuthorizationServerConfigurationTest {
  @Mock
  private AuthenticationManager authenticationManagerFixture;

  @Mock
  private ClientDetailsServiceImpl clientDetailsServiceFixture;

  @Mock
  private DefaultTokenServices tokenServiceFixture;

  @Mock
  private LoggingExceptionTranslator loggingExceptionTranslatorFixture;

  @Mock
  private TokenStore tokenStoreFixture;

  @Mock
  private UserDetailsServiceImpl userDetailsServiceFixture;

  @Spy
  @InjectMocks
  private OAuth2AuthorizationServerConfiguration fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void oAuth2AuthorizationServerConfigurationShouldBeAnnotated() {
    assertThat(OAuth2AuthorizationServerConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableAuthorizationServer.class);
  }

  @Test
  public void oAuth2AuthorizationServerConfigurationShouldHavePublicConstructor() {
    assertThat(new OAuth2AuthorizationServerConfiguration()).isNotNull();
  }

  @Test
  public void configureAuthorizationServerEndpointsConfigurerShouldInitializeCorrectConfiguration() throws Exception {
    AuthorizationServerEndpointsConfigurer configurer = Mockito.spy(AuthorizationServerEndpointsConfigurer.class);

    fixture.configure(configurer);

    verify(configurer, times(1)).authenticationManager(authenticationManagerFixture);
    verify(configurer, times(1)).exceptionTranslator(loggingExceptionTranslatorFixture);
    verify(configurer, times(1)).reuseRefreshTokens(false);
    verify(configurer, times(1)).tokenServices(tokenServiceFixture);
    verify(configurer, times(1)).tokenStore(tokenStoreFixture);
    verify(configurer, times(1)).userDetailsService(userDetailsServiceFixture);
  }

  @Test
  public void configureAuthorizationServerSecurityConfigurerShouldInitializeCorrectConfiguration() throws Exception {
    AuthorizationServerSecurityConfigurer configurer = Mockito.spy(AuthorizationServerSecurityConfigurer.class);

    fixture.configure(configurer);

    verify(configurer, times(1)).tokenKeyAccess("permitAll()");
    verify(configurer, times(1)).checkTokenAccess("isAuthenticated()");
  }

  @Test
  public void configureClientDetailsServiceConfigurerShouldInitializeCorrectConfiguration() throws Exception {
    ClientDetailsServiceConfigurer configurer = Mockito.mock(ClientDetailsServiceConfigurer.class);

    fixture.configure(configurer);

    verify(configurer, times(1)).withClientDetails(clientDetailsServiceFixture);
  }
}