package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;

import ch.tbmelabs.tv.core.authorizationserver.configuration.TokenServiceConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.service.clientdetails.ClientDetailsServiceImpl;

public class TokenServiceConfigurationTest {
  @Mock
  private AuthenticationManager authenticationManagerFixture;

  @Mock
  private ClientDetailsServiceImpl clientDetailsServiceFixture;

  @Mock
  private TokenStore tokenStoreFixture;

  @Spy
  @InjectMocks
  private TokenServiceConfiguration fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).tokenServiceBean();
  }

  @Test
  public void tokenServiceConfigurationShouldBeAnnotated() {
    assertThat(TokenServiceConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void tokenServiceConfigurationShouldHavePublicConstructor() {
    assertThat(new TokenServiceConfiguration()).isNotNull();
  }

  @Test
  public void tokenServiceBeanShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    assertThat(TokenServiceConfiguration.class.getDeclaredMethod("tokenServiceBean", new Class<?>[] {})
        .getDeclaredAnnotation(Bean.class)).isNotNull();
  }

  @Test
  public void tokenServiceBeanShouldReturnATokenService()
      throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
    assertThat(fixture.tokenServiceBean()).isNotNull()
        .hasFieldOrPropertyWithValue("authenticationManager", authenticationManagerFixture)
        .hasFieldOrPropertyWithValue("clientDetailsService", clientDetailsServiceFixture)
        .hasFieldOrPropertyWithValue("reuseRefreshToken", false)
        .hasFieldOrPropertyWithValue("tokenStore", tokenStoreFixture);
  }
}