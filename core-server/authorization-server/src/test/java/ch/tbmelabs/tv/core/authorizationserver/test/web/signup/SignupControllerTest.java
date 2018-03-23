package ch.tbmelabs.tv.core.authorizationserver.test.web.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.core.authorizationserver.web.signup.SignupController;

public class SignupControllerTest {
  @Mock
  private UserSignupService userSignupServiceFixture;

  @Spy
  @InjectMocks
  private SignupController fixture;

  @Before
  public void beforeClassSetUp() {
    initMocks(this);

    doReturn(true).when(userSignupServiceFixture).isUsernameUnique(Mockito.any(User.class));
    doReturn(true).when(userSignupServiceFixture).doesUsernameMatchFormat(Mockito.any(User.class));
    doReturn(true).when(userSignupServiceFixture).isEmailAddressUnique(Mockito.any(User.class));
    doReturn(true).when(userSignupServiceFixture).isEmailAddress(Mockito.any(User.class));
    doReturn(true).when(userSignupServiceFixture).doesPasswordMatchFormat(Mockito.any(User.class));
    doReturn(true).when(userSignupServiceFixture).doPasswordsMatch(Mockito.any(User.class));
  }

  @Test
  public void signupControllerShouldBeAnnotated() {
    assertThat(SignupController.class).hasAnnotation(RestController.class).hasAnnotation(RequestMapping.class);
    assertThat(SignupController.class.getDeclaredAnnotation(RequestMapping.class).value()).isNotEmpty()
        .containsExactly("/signup");
  }

  @Test
  public void signupControllerShouldHavePublicConstructor() {
    assertThat(new SignupController()).isNotNull();
  }

  @Test
  public void signupShouldCallServiceMethod() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("signup", new Class<?>[] { User.class });
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value()).isNotEmpty().containsExactly("/do-signup");

    fixture.signup(new User());

    verify(userSignupServiceFixture, times(1)).signUpNewUser(Mockito.any(User.class));
  }

  @Test
  public void isUsernameUniqueShouldCallServiceMethod() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("isUsernameUnique", new Class<?>[] { User.class });
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value()).isNotEmpty()
        .containsExactly("/is-username-unique");

    fixture.isUsernameUnique(new User());

    verify(userSignupServiceFixture, times(1)).isUsernameUnique(Mockito.any(User.class));
  }

  @Test
  public void doesUsernameMatchFormatShouldCallServiceMethod() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doesUsernameMatchFormat", new Class<?>[] { User.class });
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value()).isNotEmpty()
        .containsExactly("/does-username-match-format");

    fixture.doesUsernameMatchFormat(new User());

    verify(userSignupServiceFixture, times(1)).doesUsernameMatchFormat(Mockito.any(User.class));
  }

  @Test
  public void isEmailAddressUniqueShouldCallServiceMethod() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("isEmailAddressUnique", new Class<?>[] { User.class });
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value()).isNotEmpty()
        .containsExactly("/is-email-unique");

    fixture.isEmailAddressUnique(new User());

    verify(userSignupServiceFixture, times(1)).isEmailAddressUnique(Mockito.any(User.class));
  }

  @Test
  public void isEmailAddressShouldCallServiceMethod() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("isEmailAddress", new Class<?>[] { User.class });
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value()).isNotEmpty().containsExactly("/is-email");

    fixture.isEmailAddress(new User());

    verify(userSignupServiceFixture, times(1)).isEmailAddress(Mockito.any(User.class));
  }

  @Test
  public void doesPasswordMatchFormatShouldCallServiceMethod() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doesPasswordMatchFormat", new Class<?>[] { User.class });
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value()).isNotEmpty()
        .containsExactly("/does-password-match-format");

    fixture.doesPasswordMatchFormat(new User());

    verify(userSignupServiceFixture, times(1)).doesPasswordMatchFormat(Mockito.any(User.class));
  }

  @Test
  public void doPasswordsMatchShouldCallServiceMethod() throws NoSuchMethodException, SecurityException {
    Method signup = SignupController.class.getDeclaredMethod("doPasswordsMatch", new Class<?>[] { User.class });
    assertThat(signup.getDeclaredAnnotation(PostMapping.class).value()).isNotEmpty()
        .containsExactly("/do-passwords-match");

    fixture.doPasswordsMatch(new User());

    verify(userSignupServiceFixture, times(1)).doPasswordsMatch(Mockito.any(User.class));
  }
}