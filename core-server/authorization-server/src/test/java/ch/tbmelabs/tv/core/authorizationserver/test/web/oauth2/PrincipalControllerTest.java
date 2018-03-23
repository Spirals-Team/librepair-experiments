package ch.tbmelabs.tv.core.authorizationserver.test.web.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.web.oauth2.PrincipalController;

public class PrincipalControllerTest {
  @Test
  public void principalControllerShouldBeAnnotated() {
    assertThat(PrincipalController.class).hasAnnotation(RestController.class);
  }

  @Test
  public void principalControllerShouldHavePublicConstructor() {
    assertThat(new PrincipalController()).isNotNull();
  }

  @Test
  public void getPrincipalShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method fixture = PrincipalController.class.getDeclaredMethod("getPrincipal", new Class<?>[] {});
    assertThat(fixture.getDeclaredAnnotation(RequestMapping.class).value()).isNotEmpty().containsExactly("/me",
        "/user");
  }

  @Test
  public void getProfileShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    Method fixture = PrincipalController.class.getDeclaredMethod("getProfile", new Class<?>[] {});
    assertThat(fixture.getDeclaredAnnotation(RequestMapping.class).value()).isNotEmpty().containsExactly("/profile");
  }
}