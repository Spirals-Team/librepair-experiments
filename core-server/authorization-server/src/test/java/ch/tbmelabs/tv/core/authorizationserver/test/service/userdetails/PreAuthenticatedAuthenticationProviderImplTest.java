package ch.tbmelabs.tv.core.authorizationserver.test.service.userdetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.PreAuthenticatedAuthenticationProviderImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.PreAuthenticationUserDetailsServiceImpl;

public class PreAuthenticatedAuthenticationProviderImplTest {
  @Mock
  private PreAuthenticationUserDetailsServiceImpl mockPreAuthenticationUserDetailsServiceImpl;

  @Spy
  @InjectMocks
  private PreAuthenticatedAuthenticationProviderImpl fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).initBean();
  }

  @Test
  public void preAuthenticatedAuthenticationProviderImplShouldBeAnnotated() {
    assertThat(PreAuthenticatedAuthenticationProviderImpl.class).hasAnnotation(Component.class);
  }

  @Test
  public void preAuthenticatedAuthenticationProviderImplShouldExtendPreAuthenticatedAuthenticationProvider() {
    assertThat(PreAuthenticatedAuthenticationProvider.class)
        .isAssignableFrom(PreAuthenticatedAuthenticationProviderImpl.class);
  }

  @Test
  public void preAuthenticatedAuthenticationProviderImplShouldHavePublicConstructor() {
    assertThat(new PreAuthenticatedAuthenticationProviderImpl()).isNotNull();
  }

  @Test
  public void initBeanShouldAssignUserDetailsService() {
    fixture.initBean();

    assertThat(ReflectionTestUtils.getField(fixture, "preAuthenticatedUserDetailsService"))
        .isEqualTo(mockPreAuthenticationUserDetailsServiceImpl);
  }
}