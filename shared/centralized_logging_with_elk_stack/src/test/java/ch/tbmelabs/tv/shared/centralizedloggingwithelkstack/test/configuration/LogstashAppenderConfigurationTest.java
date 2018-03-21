package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.MockitoAnnotations.initMocks;

import org.apache.logging.log4j.LogManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration.LogstashAppenderConfiguration;

public class LogstashAppenderConfigurationTest {
  private static final String NO_APPLICATION_NAME_ERROR_MESSAGE = "Specify an application name (\"spring.application.name\") to use the centralized logging.";

  @Mock
  private LogstashAppenderConfiguration fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "logstashHost", "localhost");
    ReflectionTestUtils.setField(fixture, "logstashPort", 5000);

    doCallRealMethod().when(fixture).initBean();
  }

  @Test
  public void logstashAppenderConfigurationShouldBeAnnotated() {
    assertThat(LogstashAppenderConfiguration.class).hasAnnotation(Configuration.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void initBeanShouldThrowExceptionIfApplicationNameIsNull() {
    try {
      fixture.initBean();
    } catch (Exception e) {
      assertThat(e).isOfAnyClassIn(IllegalArgumentException.class);
      assertThat(e.getLocalizedMessage()).isEqualTo(NO_APPLICATION_NAME_ERROR_MESSAGE);
      throw e;
    }
  }

  @Test
  public void logstashAppenderShouldBeRegisteredToRootLogger() {
    ReflectionTestUtils.setField(fixture, "serviceName", "testapplication");

    fixture.initBean();

    assertThat(((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).getAppenders())
        .containsKey(LogstashAppenderConfiguration.getAppenderName());
    assertThat(((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).getAppenders()
        .get(LogstashAppenderConfiguration.getAppenderName())).isNotNull();
  }

  @Test
  public void appenderNameGetterAndSetterWorkAsExpected() {
    String appenderName = "anotherappender";

    assertThat(LogstashAppenderConfiguration.getAppenderName()).isEqualTo("logstash");

    LogstashAppenderConfiguration.setAppenderName(appenderName);

    assertThat(fixture).hasFieldOrPropertyWithValue("appenderName", appenderName);
  }
}