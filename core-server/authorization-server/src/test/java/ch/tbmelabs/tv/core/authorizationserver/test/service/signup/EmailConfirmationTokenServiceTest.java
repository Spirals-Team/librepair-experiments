package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.EmailConfirmationToken;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.EmailConfirmationTokenCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.EmailConfirmationTokenService;

public class EmailConfirmationTokenServiceTest {
  @Mock
  private EmailConfirmationTokenCRUDRepository mockEmailConfirmationTokenRepository;

  @Spy
  @InjectMocks
  private EmailConfirmationTokenService fixture;

  private static EmailConfirmationToken savedToken;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(null).when(mockEmailConfirmationTokenRepository).findByTokenString(Mockito.anyString());
    doAnswer(new Answer<EmailConfirmationToken>() {
      @Override
      public EmailConfirmationToken answer(InvocationOnMock invocation) throws Throwable {
        EmailConfirmationToken newEmailConfirmationToken = invocation.getArgument(0);
        newEmailConfirmationToken.setId(new Random().nextLong());

        EmailConfirmationTokenServiceTest.savedToken = newEmailConfirmationToken;

        return newEmailConfirmationToken;
      }
    }).when(mockEmailConfirmationTokenRepository).save(Mockito.any(EmailConfirmationToken.class));
  }

  @Test
  public void emailConfirmationTokenSerivceShouldBeAnnotated() {
    assertThat(EmailConfirmationTokenService.class).hasAnnotation(Service.class);
  }

  @Test
  public void emailConfirmationTokenServiceShouldHavePublicConstructor() {
    assertThat(new EmailConfirmationTokenService()).isNotNull();
  }

  @Test
  public void createUniqueEmailConfirmationTokenShouldReturnNewUUID() {
    User user = Mockito.mock(User.class);

    String token = fixture.createUniqueEmailConfirmationToken(user);

    assertThat(UUID.fromString(token)).isNotNull();
    assertThat(EmailConfirmationTokenServiceTest.savedToken.getUser()).isEqualTo(user);
    assertThat(EmailConfirmationTokenServiceTest.savedToken.getTokenString()).isEqualTo(token);
  }
}