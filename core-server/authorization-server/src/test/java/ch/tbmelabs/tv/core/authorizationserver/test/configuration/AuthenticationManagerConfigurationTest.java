package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.configuration.AuthenticationManagerConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.PreAuthenticatedAuthenticationProviderImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;

public class AuthenticationManagerConfigurationTest {
  @Mock
  private PreAuthenticatedAuthenticationProviderImpl preAuthProviderImplFixture;

  @Mock
  private UserDetailsServiceImpl userDetailsServiceImplFixture;

  @Spy
  @InjectMocks
  private AuthenticationManagerConfiguration fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "objectPostProcessor", new ObjectPostProcessor<Object>() {
      @Override
      public <O extends Object> O postProcess(O object) {
        return object;
      }
    });
  }

  @Test
  public void authenticationManagerConfigurationShouldBeAnnotated() {
    assertThat(AuthenticationManagerConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void authenticationManagerBeanShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    assertThat(AuthenticationManagerConfiguration.class.getDeclaredMethod("authenticationManager", new Class<?>[] {})
        .getDeclaredAnnotation(Bean.class)).isNotNull();
  }

  @Test
  public void authenticationManagerBeanShouldReturnAnAuthenticationManager() throws Exception {
    assertThat(((ProviderManager) fixture.authenticationManager()).getProviders()).isNotNull().hasSize(2)
        .contains(preAuthProviderImplFixture);

    Optional<AuthenticationProvider> daoProvider = ((ProviderManager) fixture.authenticationManager()).getProviders()
        .stream().filter(provider -> DaoAuthenticationProvider.class.isAssignableFrom(provider.getClass())).findFirst();

    assertThat(daoProvider.isPresent()).isTrue();
    assertThat((DaoAuthenticationProvider) daoProvider.get()).hasFieldOrPropertyWithValue("userDetailsService",
        userDetailsServiceImplFixture);
  }
}