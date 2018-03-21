package ch.tbmelabs.core.servicediscovery.test.application;

import org.junit.Test;

import ch.tbmelabs.core.servicediscovery.test.AbstractEurekaApplicationContextAware;
import ch.tbmelabs.tv.core.servicediscovery.Application;

public class ApplicationIntTest extends AbstractEurekaApplicationContextAware {
  @Test
  public void applicationContextLoads() {
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[] { "--spring.profiles.active=test" });
  }
}