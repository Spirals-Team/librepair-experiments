package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import ch.tbmelabs.tv.core.authorizationserver.configuration.FlywayConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class FlywayConfigurationTest {
  @Test
  public void springMailConfigurationShouldBeAnnotated() {
    assertThat(FlywayConfiguration.class).hasAnnotation(Configuration.class).hasAnnotation(Profile.class)
        .hasAnnotation(PropertySource.class);

    assertThat(FlywayConfiguration.class.getDeclaredAnnotation(Profile.class).value()).hasSize(1)
        .containsExactly("!" + SpringApplicationProfile.TEST);
    assertThat(FlywayConfiguration.class.getDeclaredAnnotation(PropertySource.class).value()).hasSize(1)
        .containsExactly("classpath:configuration/flyway.properties");
  }

  @Test
  public void springMailConfigurationShouldHavePublicConstructor() {
    assertThat(new FlywayConfiguration()).isNotNull();
  }
}