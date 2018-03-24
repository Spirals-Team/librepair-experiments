package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import ch.tbmelabs.tv.core.authorizationserver.configuration.SpringMailConfiguration;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class SpringMailConfigurationTest {
  @Test
  public void springMailConfigurationShouldBeAnnotated() {
    assertThat(SpringMailConfiguration.class).hasAnnotation(Configuration.class).hasAnnotation(Profile.class)
        .hasAnnotation(PropertySource.class);

    assertThat(SpringMailConfiguration.class.getDeclaredAnnotation(Profile.class).value()).hasSize(1)
        .containsExactly("!" + SpringApplicationProfile.NO_MAIL);
    assertThat(SpringMailConfiguration.class.getDeclaredAnnotation(PropertySource.class).value()).hasSize(1)
        .containsExactly("classpath:configuration/mail.properties");
  }

  @Test
  public void springMailConfigurationShouldHavePublicConstructor() {
    assertThat(new SpringMailConfiguration()).isNotNull();
  }
}