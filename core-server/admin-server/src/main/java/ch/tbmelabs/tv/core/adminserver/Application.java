package ch.tbmelabs.tv.core.adminserver;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.core.env.Environment;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@SpringCloudApplication
public class Application extends SpringBootServletInitializer {
  private static final Class<Application> APPLICATION_SOURCE_CLASS = Application.class;

  @Autowired
  private Environment environment;

  @PostConstruct
  public void initBean() {
    List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());

    if (activeProfiles.contains(SpringApplicationProfile.PROD)
        && activeProfiles.contains(SpringApplicationProfile.DEV)) {
      throw new IllegalArgumentException(
          "Do not attempt to run an application in productive and development environment at the same time!");
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(APPLICATION_SOURCE_CLASS, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
    return applicationBuilder.sources(APPLICATION_SOURCE_CLASS);
  }
}