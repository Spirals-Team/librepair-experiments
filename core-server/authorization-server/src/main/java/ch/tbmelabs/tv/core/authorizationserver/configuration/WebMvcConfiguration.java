package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/");
    resolver.setSuffix(".html");
    return resolver;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations("/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // TODO: How to deny access to anonymous urls for authenticated users?
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/signin").setViewName("signin");
    registry.addViewController("/signup").setViewName("signup");
    registry.addViewController("/oauth/confirm_access").setViewName("authorize");

    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }
}