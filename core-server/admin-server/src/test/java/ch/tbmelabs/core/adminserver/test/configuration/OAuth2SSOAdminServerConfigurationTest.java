package ch.tbmelabs.core.adminserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import ch.tbmelabs.tv.core.adminserver.configuration.OAuth2SSOAdminServerConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import de.codecentric.boot.admin.config.EnableAdminServer;

public class OAuth2SSOAdminServerConfigurationTest {
  @Mock
  private Environment mockEnvironment;

  @Spy
  @InjectMocks
  private OAuth2SSOAdminServerConfiguration fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);

    doReturn(new String[] { SpringApplicationProfile.DEV }).when(mockEnvironment).getActiveProfiles();
  }

  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOAdminServerConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableOAuth2Sso.class).hasAnnotation(EnableAdminServer.class);
  }

  @Test
  public void eurekaConfigurationShouldHavePublicConstructor() {
    assertThat(new OAuth2SSOAdminServerConfiguration()).isNotNull();
  }

  @Test
  public void configurationShouldDebugHttpRequestsIfDevelopmentProfileIsActive() throws Exception {
    WebSecurity security = Mockito.mock(WebSecurity.class);

    fixture.configure(security);

    verify(security, times(1)).debug(true);
  }
}