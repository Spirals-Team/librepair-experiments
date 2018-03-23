package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.test.production;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.production.ProductiveEnvironmentWithoutCentralizedLoggingCheck;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ProductiveEnvironmentWithoutCentralizedLoggingCheckTest {
  @Mock
  private Environment environment;

  @Spy
  @InjectMocks
  private ProductiveEnvironmentWithoutCentralizedLoggingCheck fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    when(environment.getActiveProfiles()).thenReturn(new String[] { SpringApplicationProfile.PROD });

    doCallRealMethod().when(fixture).initBean();
  }

  @Test
  public void productiveEnvironmentWithoutCentralizedLoggingCheckShouldBeAnnotated() {
    assertThat(ProductiveEnvironmentWithoutCentralizedLoggingCheck.class).hasAnnotation(Component.class);
    assertThat(ProductiveEnvironmentWithoutCentralizedLoggingCheck.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.PROD);
  }

  @Test
  public void checkShouldWarnUserIfProductiveEnvironmentWithoutCentralizedLoggingIsUsed()
      throws UnsupportedEncodingException {
    ByteArrayOutputStream mockOut = new ByteArrayOutputStream();

    ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger())
        .addAppender(OutputStreamAppender.newBuilder().setName("mock").setTarget(mockOut).build());

    fixture.initBean();

    assertThat(mockOut.toString(StandardCharsets.UTF_8.name()))
        .contains("Found active profile " + SpringApplicationProfile.PROD + " without " + SpringApplicationProfile.ELK)
        .contains("Consider monitoring your application with the ELK stack");
  }
}