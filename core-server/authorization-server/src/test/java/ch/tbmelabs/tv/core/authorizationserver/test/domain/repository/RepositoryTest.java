package ch.tbmelabs.tv.core.authorizationserver.test.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.tbmelabs.tv.core.authorizationserver.Application;

public class RepositoryTest {
  private static final Integer EXPECTED_REPOSITORY_COUNT = 14;

  @Test
  public void packageShouldOnlyContainRepositories() {
    new Reflections(Application.class.getPackage().getName() + ".domain.repository").getSubTypesOf(Object.class)
        .forEach(repository -> assertThat(repository.getClass().getSimpleName()).contains("Repository"));
  }

  @Test
  public void allRepositoriesShouldBeAnnotated() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain.repository")
        .getTypesAnnotatedWith(Repository.class)).hasSize(EXPECTED_REPOSITORY_COUNT);
  }

  @Test
  public void allRepositoriesShouldExtendsTheCRUDRepositoryInterface() {
    assertThat(new Reflections(Application.class.getPackage().getName() + ".domain.repository")
        .getSubTypesOf(CrudRepository.class)).hasSize(EXPECTED_REPOSITORY_COUNT);
  }
}