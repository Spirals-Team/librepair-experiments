package ch.tbmelabs.tv.core.authorizationserver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  public static ApplicationContext getApplicationContext() {
    return ApplicationContextHolder.applicationContext;
  }

  private static void setStaticApplicationContext(ApplicationContext applicationContext) {
    ApplicationContextHolder.applicationContext = applicationContext;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    ApplicationContextHolder.setStaticApplicationContext(applicationContext);
  }
}