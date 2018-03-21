package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Test;
import org.springframework.context.annotation.Import;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.annotation.EnableCentralizedLogging;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.LogstashAppenderConfiguration;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.SleuthSamplerConfiguration;
import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.production.ProductiveEnvironmentWithoutCentralizedLoggingCheck;

public class EnableCentralizedLoggingTest {
  @Test
  public void annotationShouldApplyToTypesOnly() {
    assertThat(EnableCentralizedLogging.class).hasAnnotation(Target.class);
    assertThat(EnableCentralizedLogging.class.getDeclaredAnnotation(Target.class).value()).isNotNull()
        .containsExactly(ElementType.TYPE);
  }

  @Test
  public void annotationShouldApplyToRetentionTargetRuntime() {
    assertThat(EnableCentralizedLogging.class).hasAnnotation(Retention.class);
    assertThat(EnableCentralizedLogging.class.getDeclaredAnnotation(Retention.class).value()).isNotNull()
        .isEqualTo(RetentionPolicy.RUNTIME);
  }

  @Test
  public void annotationShouldImportConfigurationClasses() {
    assertThat(EnableCentralizedLogging.class).hasAnnotation(Import.class);
    assertThat(EnableCentralizedLogging.class.getDeclaredAnnotation(Import.class).value()).isNotNull().containsExactly(
        LogstashAppenderConfiguration.class, SleuthSamplerConfiguration.class,
        ProductiveEnvironmentWithoutCentralizedLoggingCheck.class);
  }
}