package ch.tbmelabs.core.servicediscovery.test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import ch.tbmelabs.tv.core.servicediscovery.Application;

public class SpringBootServletInitializerConfigurationTest {
  private static final String APPLICATION_CLASS_FIELD_NAME = "APPLICATION_SOURCE_CLASS";

  @Test
  public void applicationExtendsSpringBootServletInitializer() {
    assertThat(SpringBootServletInitializer.class).isAssignableFrom(Application.class);
  }

  @Test
  public void applicationSourceShouldBePassedToSpringApplication()
      throws InstantiationException, IllegalAccessException {
    assertThat(Application.class).hasDeclaredFields(APPLICATION_CLASS_FIELD_NAME);
    assertThat(Application.class.newInstance()).hasFieldOrPropertyWithValue(APPLICATION_CLASS_FIELD_NAME,
        Application.class);
  }
}