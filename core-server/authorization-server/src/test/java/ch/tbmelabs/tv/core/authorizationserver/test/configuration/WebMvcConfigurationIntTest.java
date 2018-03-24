package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ch.tbmelabs.tv.core.authorizationserver.configuration.WebMvcConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class WebMvcConfigurationIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private ServletContext servletContext;

  @Autowired
  private WebMvcConfiguration configuration;

  @Autowired
  private InternalResourceViewResolver bean;

  @Test
  public void internalResourceViewResolverBeanShouldEqualConfiguredViewResolver() {
    assertThat(configuration.viewResolver()).isEqualTo(bean);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void resourceHandlerRegistryShouldBeConfiguredCorrectly() {
    ResourceHandlerRegistry registry = new ResourceHandlerRegistry(applicationContext, servletContext);
    configuration.addResourceHandlers(registry);

    List<ResourceHandlerRegistration> registrations = (List<ResourceHandlerRegistration>) ReflectionTestUtils
        .getField(registry, "registrations");

    assertThat(registrations).isNotNull().hasSize(1);
    assertThat((String[]) ReflectionTestUtils.getField(registrations.get(0), "pathPatterns")).isNotNull().hasSize(1)
        .containsExactly("/**");
    assertThat((ArrayList<String>) ReflectionTestUtils.getField(registrations.get(0), "locationValues")).isNotNull()
        .hasSize(1);
    assertThat(((ArrayList<String>) ReflectionTestUtils.getField(registrations.get(0), "locationValues")).get(0))
        .isEqualTo("/");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void viewControllerRegistryShouldBeConfiguredCorrectly() {
    ViewControllerRegistry registry = new ViewControllerRegistry(applicationContext);
    ReflectionTestUtils.setField(registry, "applicationContext", applicationContext);

    configuration.addViewControllers(registry);

    List<ViewControllerRegistration> registrations = (List<ViewControllerRegistration>) ReflectionTestUtils
        .getField(registry, "registrations");

    assertThat(registrations).isNotNull().hasSize(4);
    assertThat(registry).hasFieldOrPropertyWithValue("order", Ordered.HIGHEST_PRECEDENCE);

    registrations.forEach(view -> {
      switch ((String) ReflectionTestUtils.getField(view, "urlPath")) {
      case "/":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("index");
        break;
      case "/signin":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("signin");
        break;
      case "/signup":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("signup");
        break;
      case "/oauth/confirm_access":
        assertThat(((ParameterizableViewController) ReflectionTestUtils.getField(view, "controller")).getViewName())
            .isEqualTo("authorize");
        break;
      default:
        throw new IllegalArgumentException("Unexpected view!");
      }
    });
  }
}