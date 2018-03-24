package ch.tbmelabs.tv.core.authorizationserver.test.service.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.oauth2.PrincipalService;
import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

public class PrincipalServiceTest {
  private static final String UNKNOWN_USERNAME_ERROR = "Cannot identify authenticated user: Please try again!";

  @Mock
  private Authentication mockedAnonymousAuthentication;

  @Mock
  private UserCRUDRepository mockUserRepository;

  @Spy
  @InjectMocks
  private PrincipalService fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new User()).when(mockUserRepository).findByUsername(Mockito.anyString());

    doReturn(Arrays.asList(new Role(SecurityRole.ANONYMOUS))).when(mockedAnonymousAuthentication).getAuthorities();
    SecurityContextHolder.getContext().setAuthentication(mockedAnonymousAuthentication);
  }

  @Test
  public void principalServiceShouldBeAnnotated() {
    assertThat(PrincipalService.class).hasAnnotation(Service.class);
  }

  @Test
  public void principalServiceShouldHavePublicConstructor() {
    assertThat(new PrincipalService()).isNotNull();
  }

  @Test
  public void getCurrentUserShouldReturnNullOnAnonymousUser() {
    assertThat(fixture.getCurrentUser()).isNull();
  }

  @Test
  public void getCurrentUserShouldReturnCorrectAuthenticatedUser() {
    doReturn(RandomStringUtils.random(11)).when(mockedAnonymousAuthentication).getName();
    doReturn(Arrays.asList(new Role(SecurityRole.USER))).when(mockedAnonymousAuthentication).getAuthorities();

    assertThat(fixture.getCurrentUser()).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCurrentUserShouldThrowExceptionIfUserCannotBeIdentified() {
    doReturn(null).when(mockUserRepository).findByUsername(Mockito.anyString());

    doReturn(RandomStringUtils.random(11)).when(mockedAnonymousAuthentication).getName();
    doReturn(Arrays.asList(new Role(SecurityRole.USER))).when(mockedAnonymousAuthentication).getAuthorities();

    try {
      fixture.getCurrentUser();
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e).hasMessage(UNKNOWN_USERNAME_ERROR);

      throw e;
    }
  }

  @Test
  public void isAuthenticatedShouldReturnFalseIfUserHasAnonymousRole() {
    assertThat(PrincipalService.isAuthenticated()).isFalse();
  }

  @Test
  public void isAuthenticatedShouldReturnTrueIfUserHasNoAnonymousRole() {
    doReturn(Arrays.asList(new Role(SecurityRole.USER))).when(mockedAnonymousAuthentication).getAuthorities();

    assertThat(PrincipalService.isAuthenticated()).isTrue();
  }
}