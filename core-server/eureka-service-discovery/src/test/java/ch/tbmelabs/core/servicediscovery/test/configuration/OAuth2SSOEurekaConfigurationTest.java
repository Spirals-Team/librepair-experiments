package ch.tbmelabs.core.servicediscovery.test.configuration;

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
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import ch.tbmelabs.tv.core.servicediscovery.configuration.OAuth2SSOEurekaConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class OAuth2SSOEurekaConfigurationTest {
  @Mock
  private Environment mockEnvironment;

  @Spy
  @InjectMocks
  private OAuth2SSOEurekaConfiguration fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);

    doReturn(new String[] { SpringApplicationProfile.DEV }).when(mockEnvironment).getActiveProfiles();
  }

  @Test
  public void eurekaConfigurationShouldBeAnnotated() {
    assertThat(OAuth2SSOEurekaConfiguration.class).hasAnnotation(Configuration.class)
        .hasAnnotation(EnableOAuth2Sso.class).hasAnnotation(EnableEurekaServer.class);
  }

  @Test
  public void eurekaConfigurationShouldHavePublicConstructor() {
    assertThat(new OAuth2SSOEurekaConfiguration()).isNotNull();
  }

  @Test
  public void configurationShouldDebugHttpRequestsIfDevelopmentProfileIsActive() throws Exception {
    WebSecurity security = Mockito.mock(WebSecurity.class);

    fixture.configure(security);

    verify(security, times(1)).debug(true);
  }
}