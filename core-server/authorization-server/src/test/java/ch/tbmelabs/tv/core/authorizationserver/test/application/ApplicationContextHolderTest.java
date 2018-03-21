package ch.tbmelabs.tv.core.authorizationserver.test.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.core.authorizationserver.ApplicationContextHolder;

public class ApplicationContextHolderTest {
  @Mock
  private ApplicationContext applicationContextFixture;

  @Spy
  @InjectMocks
  private ApplicationContextHolder fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).setApplicationContext(applicationContextFixture);
  }

  @Test
  public void applicationContextHolderShouldBeAnnotated() {
    assertThat(ApplicationContextHolder.class).hasAnnotation(Component.class);
  }

  @Test
  public void applicationContextHolderShouldHavePublicConstructor() {
    assertThat(new ApplicationContextHolder()).isNotNull();
  }

  @Test
  public void aware() {
    assertThat(ApplicationContextAware.class).isAssignableFrom(ApplicationContextHolder.class);
  }

  @Test
  public void applicationContextGetterAndSetterWorkAsExpected() {
    fixture.setApplicationContext(applicationContextFixture);

    assertThat(fixture).hasFieldOrPropertyWithValue("applicationContext", applicationContextFixture);

    assertThat(ApplicationContextHolder.getApplicationContext()).isEqualTo(applicationContextFixture);
  }
}