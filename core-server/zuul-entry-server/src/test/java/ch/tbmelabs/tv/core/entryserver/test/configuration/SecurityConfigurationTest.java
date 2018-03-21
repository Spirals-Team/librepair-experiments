package ch.tbmelabs.tv.core.entryserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import ch.tbmelabs.tv.core.entryserver.configuration.SecurityConfiguration;

public class SecurityConfigurationTest {
  @Test
  public void securityConfigurationShouldBeAnnotated() {
    assertThat(SecurityConfiguration.class).hasAnnotation(Configuration.class).hasAnnotation(EnableWebSecurity.class)
        .hasAnnotation(EnableGlobalMethodSecurity.class);
  }

  @Test
  public void securityConfigHasPublicConstructor() {
    assertThat(new SecurityConfiguration()).isNotNull();
  }
}