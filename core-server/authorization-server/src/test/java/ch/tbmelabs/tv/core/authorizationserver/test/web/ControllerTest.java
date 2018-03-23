package ch.tbmelabs.tv.core.authorizationserver.test.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.Application;

public class ControllerTest {
  private static final Integer EXPECTED_CONTROLLER_COUNT = 4;

  private static Set<Class<?>> allControllers = new HashSet<>();
  private static Set<Class<?>> annotatedControllers = new HashSet<>();

  @BeforeClass
  public static void beforeClassSetUp() {
    allControllers
        .addAll(new Reflections(Application.class.getPackage().getName() + ".web").getSubTypesOf(Object.class));

    annotatedControllers.addAll(
        new Reflections(Application.class.getPackage().getName() + ".web").getTypesAnnotatedWith(Controller.class));
    annotatedControllers.addAll(
        new Reflections(Application.class.getPackage().getName() + ".web").getTypesAnnotatedWith(RestController.class));
  }

  @Test
  public void packageShouldOnlyContainControllers() {
    allControllers.forEach(controller -> assertThat(controller.getClass().getSimpleName()).contains("Controller"));
  }

  @Test
  public void allControllersShouldBeAnnotated() {
    assertThat(ControllerTest.annotatedControllers).hasSize(EXPECTED_CONTROLLER_COUNT);
  }

  @Test
  public void allMappingsShouldSpecifyARequestMethod() {
    annotatedControllers
        .forEach(controller -> assertThat(Arrays.stream(controller.getDeclaredMethods())
            .filter(method -> method.getDeclaredAnnotation(RequestMapping.class) != null
                && method.getDeclaredAnnotation(RequestMapping.class).method() == null)
            .findAny().isPresent()).isFalse());
  }
}