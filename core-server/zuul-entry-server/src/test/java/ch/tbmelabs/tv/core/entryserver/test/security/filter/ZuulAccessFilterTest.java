package ch.tbmelabs.tv.core.entryserver.test.security.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.apache.logging.log4j.core.net.Protocol;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import ch.tbmelabs.tv.core.entryserver.security.filter.ZuulAccessFilter;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

public class ZuulAccessFilterTest {
  @Mock
  private static MockHttpServletRequest requestFixture;

  @Mock
  private static MockHttpServletResponse responseFixture;

  @Mock
  private ZuulAccessFilter fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn("HTTPS").when(requestFixture).getScheme();
    doReturn("127.0.0.1").when(requestFixture).getLocalAddr();
    doReturn(80).when(requestFixture).getLocalPort();
    doReturn(HttpMethod.GET.name()).when(requestFixture).getMethod();
    doReturn("https://tbme.tv/").when(requestFixture).getRequestURI();
    doReturn(Protocol.SSL.name()).when(requestFixture).getProtocol();

    doReturn(HttpStatus.OK.value()).when(responseFixture).getStatus();

    doCallRealMethod().when(fixture).filterType();
    doCallRealMethod().when(fixture).filterOrder();
    doCallRealMethod().when(fixture).shouldFilter();
    doCallRealMethod().when(fixture).run();

    RequestContext.getCurrentContext().setRequest(requestFixture);
    RequestContext.getCurrentContext().setResponse(responseFixture);
  }

  @Test
  public void zuulAccessFilterShouldBeAnnotated() {
    assertThat(ZuulAccessFilter.class).hasAnnotation(Component.class);
    assertThat(ZuulAccessFilter.class.getDeclaredAnnotation(Profile.class).value())
        .containsExactly(SpringApplicationProfile.DEV);
  }

  @Test
  public void zuulAccessFilterShouldExtendZuulFilter() {
    assertThat(ZuulFilter.class).isAssignableFrom(ZuulAccessFilter.class);
  }

  @Test
  public void zuulAccessFilterShouldHavePublicConstructor() {
    assertThat(new ZuulAccessFilter()).isNotNull();
  }

  @Test
  public void filterTypeShouldBePost() {
    assertThat(fixture.filterType()).isEqualTo("post");
  }

  @Test
  public void filterOrderShouldBeOne() {
    assertThat(fixture.filterOrder()).isEqualTo(1);
  }

  @Test
  public void filterShouldBeActive() {
    assertThat(fixture.shouldFilter()).isTrue();
  }

  @Test
  public void zuulFilterShouldLogToRootLoggerOnIncomingRequest() throws UnsupportedEncodingException {
    ByteArrayOutputStream mockOut = new ByteArrayOutputStream();

    ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger())
        .addAppender(OutputStreamAppender.newBuilder().setName("mock").setTarget(mockOut).build());

    fixture.run();

    assertThat(mockOut.toString(StandardCharsets.UTF_8.name())).contains("REQUEST  :: < HTTPS 127.0.0.1:80")
        .contains("REQUEST  :: < GET https://tbme.tv/ SSL").contains("RESPONSE :: > HTTP:200");
  }
}