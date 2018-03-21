package ch.tbmelabs.tv.core.authorizationserver.test.web.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import ch.tbmelabs.tv.core.authorizationserver.web.utils.LoggingExceptionTranslator;

public class LoggingExceptionTranslatorTest {
  private static final String EXCEPTION_MESSAGE = "This is a test exception.";
  private static final IllegalArgumentException EXCEPTION = new IllegalArgumentException(EXCEPTION_MESSAGE);

  @Mock
  private ThrowableAnalyzer throwableAnalyzerFixture;

  @Mock
  private LoggingExceptionTranslator fixture;

  @Before
  public void beforeTestSetUp() throws Exception {
    initMocks(this);

    ReflectionTestUtils.setField(fixture, "throwableAnalyzer", throwableAnalyzerFixture);

    doCallRealMethod().when(fixture).translate(Mockito.any(Exception.class));
    doReturn(new Throwable[] {}).when(throwableAnalyzerFixture).determineCauseChain(Mockito.any(Throwable.class));
  }

  @Test
  public void loggingExceptionTranslatorShouldBeAnnotated() {
    assertThat(LoggingExceptionTranslator.class).hasAnnotation(Component.class);
  }

  @Test
  public void loggingExceptionTranslatorShouldExtendDefaultWebResponseExceptionTranslator() {
    assertThat(DefaultWebResponseExceptionTranslator.class).isAssignableFrom(LoggingExceptionTranslator.class);
  }

  @Test
  public void loggingExceptionTranslatorShouldTranslateExceptionToResponseEntity() throws Exception {
    ResponseEntity<OAuth2Exception> responseFixture = fixture.translate(EXCEPTION);

    assertThat(responseFixture.getBody()).hasCause(EXCEPTION).hasMessage(EXCEPTION_MESSAGE);
    assertThat(responseFixture.getStatusCodeValue()).isEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    assertThat(responseFixture.getHeaders()).hasSize(2)
        .containsExactly(entry("Cache-Control", Arrays.asList("no-store")), entry("Pragma", Arrays.asList("no-cache")));
  }
}