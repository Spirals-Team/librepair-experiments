package ch.tbmelabs.tv.core.authorizationserver.test.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.Entity;

import org.junit.Test;
import org.reflections.Reflections;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.domain.NicelyDocumentedJDBCResource;

public class EntityTest {
  private static final Integer EXPECTED_ENTITIES_COUNT = 13;

  @Test
  public void allEntitiesShouldBeAnnotated() {
    assertThat(
        new Reflections(Application.class.getPackage().getName() + ".domain").getTypesAnnotatedWith(Entity.class))
            .hasSize(EXPECTED_ENTITIES_COUNT);
  }

  @Test
  public void allEntitiesShouldExtendTheConfiguredSuperType() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain")
        .getSubTypesOf(NicelyDocumentedJDBCResource.class)).hasSize(EXPECTED_ENTITIES_COUNT);
  }
}