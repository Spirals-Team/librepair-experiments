package ch.tbmelabs.tv.core.authorizationserver.test.application;

import org.junit.Test;

import ch.tbmelabs.tv.core.authorizationserver.Application;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class ApplicationIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Test
  public void applicationContextLoads() {
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[] { "--spring.profiles.active=test,no-redis,no-mail" });
  }
}