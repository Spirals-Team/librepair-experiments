package ch.tbmelabs.tv.core.authorizationserver.test.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.tv.core.authorizationserver.exception.EmailConfirmationTokenNotFoundException;

public class EmailConfirmationTokenNotFoundExceptionTest {
  @Test
  public void emailConfirmationTokenNotFountExceptionShouldExtendException() {
    assertThat(Exception.class).isAssignableFrom(EmailConfirmationTokenNotFoundException.class);
  }

  @Test(expected = EmailConfirmationTokenNotFoundException.class)
  public void emailConfirmationTokenNotFountExceptionShouldBeThrowable()
      throws EmailConfirmationTokenNotFoundException {
    throw new EmailConfirmationTokenNotFoundException(RandomStringUtils.random(11));
  }

  @Test
  public void emailConfirmationTokenNotFountExceptionShouldContainCorrectMessage() {
    final String tokenString = RandomStringUtils.random(11);

    try {
      throw new EmailConfirmationTokenNotFoundException(tokenString);
    } catch (EmailConfirmationTokenNotFoundException e) {
      assertThat(e.getLocalizedMessage())
          .isEqualTo("Unable to find " + EmailConfirmationToken.class + ": " + tokenString);
    }
  }
}