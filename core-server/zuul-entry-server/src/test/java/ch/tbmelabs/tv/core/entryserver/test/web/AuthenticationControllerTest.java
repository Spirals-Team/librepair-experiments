package ch.tbmelabs.tv.core.entryserver.test.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.entryserver.web.AuthenticationController;

public class AuthenticationControllerTest {
  @Mock
  private Principal mockPrincipal;

  @Mock
  private AuthenticationController fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).isAuthenticated(Mockito.any(Principal.class));
  }

  @Test
  public void authenticationControllerShouldBeAnnotated() {
    assertThat(AuthenticationController.class).hasAnnotation(RestController.class).hasAnnotation(RequestMapping.class);
    assertThat(AuthenticationController.class.getDeclaredAnnotation(RequestMapping.class).value()).isNotEmpty()
        .containsExactly("/authenticated");
  }

  @Test
  public void authenticationControllerShouldHavePublicConstructor() {
    assertThat(new AuthenticationController()).isNotNull();
  }

  @Test
  public void isAuthenticatedShouldReturnFalseIfPrincipalIsNull() {
    assertThat(fixture.isAuthenticated(null)).isFalse();
  }

  @Test
  public void isAuthenticatedShouldReturnTrueIfPrincipalIsPresent() {
    assertThat(fixture.isAuthenticated(mockPrincipal)).isTrue();
  }
}