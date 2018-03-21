package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;

public class UserDetailsServiceImplTest {
  @Mock
  private UserCRUDRepository userRepositoryFixture;

  @Spy
  @InjectMocks
  private UserDetailsServiceImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(new User()).when(userRepositoryFixture).findByUsername(Mockito.anyString());
  }

  @Test
  public void userDetailsServiceImplShouldBeAnnotated() {
    assertThat(UserDetailsServiceImpl.class).hasAnnotation(Service.class);
  }

  @Test
  public void userDetailsServiceImplShouldImplementUserDetailsService() {
    assertThat(UserDetailsService.class).isAssignableFrom(UserDetailsServiceImpl.class);
  }

  @Test
  public void userDetailsServiceImplShouldHavePublicConstructor() {
    assertThat(new UserDetailsServiceImpl()).isNotNull();
  }

  @Test
  public void loadUserByUsernameShouldLoadCorrectUser()
      throws IllegalAccessException, NoSuchFieldException, SecurityException {
    UserDetailsImpl userDetails = fixture.loadUserByUsername(RandomStringUtils.random(11));

    assertThat(ReflectionTestUtils.getField(userDetails, "user"))
        .isEqualTo(userRepositoryFixture.findByUsername(RandomStringUtils.random(11)));
  }
}