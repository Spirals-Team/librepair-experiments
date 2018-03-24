package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.SleuthSamplerConfiguration;

public class SleuthSamplerConfigurationTest {
  @Mock
  private SleuthSamplerConfiguration fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doCallRealMethod().when(fixture).defaultSampler();
  }

  @Test
  public void sleuthSamplerConfigurationShouldBeAnnotated() {
    assertThat(SleuthSamplerConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test
  public void sleuthSamplerConfigurationShouldHavePublicConstructor() {
    assertThat(new SleuthSamplerConfiguration()).isNotNull();
  }

  @Test
  public void defaultSamplerShouldBeAnnotated() throws NoSuchMethodException, SecurityException {
    assertThat(SleuthSamplerConfiguration.class.getDeclaredMethod("defaultSampler", new Class<?>[] {})
        .getDeclaredAnnotation(Bean.class)).isNotNull();
  }

  @Test
  public void samplerBeanShouldReturnAnAlwaysSampler() {
    assertThat(fixture.defaultSampler()).isOfAnyClassIn(AlwaysSampler.class);
  }
}