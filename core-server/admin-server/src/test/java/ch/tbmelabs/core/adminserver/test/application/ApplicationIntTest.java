package ch.tbmelabs.core.adminserver.test.application;

import org.junit.Test;

import ch.tbmelabs.core.adminserver.test.AbstractAdminApplicationContextAware;
import ch.tbmelabs.tv.core.adminserver.Application;

public class ApplicationIntTest extends AbstractAdminApplicationContextAware {
  @Test
  public void applicationContextLoads() {
  }

  @Test
  public void publicStaticVoidMainShouldStartSpringApplication() {
    Application.main(new String[] {});
  }
}