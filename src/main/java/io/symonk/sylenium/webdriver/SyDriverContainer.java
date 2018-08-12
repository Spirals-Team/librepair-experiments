package io.symonk.sylenium.webdriver;

import io.symonk.sylenium.webdriver.factories.SyleniumDriverFactory;
import io.symonk.sylenium.webdriver.interfaces.Containerisable;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SyDriverContainer implements Containerisable {

  private Collection<Thread> collectionOfWorkerThreads = new ConcurrentLinkedQueue<>();
  private Map<Long, WebDriver> mapOfThreadedDrivers = new ConcurrentHashMap<>(5);
  private SyleniumDriverFactory factory = new SyleniumDriverFactory();
  private Proxy proxy;

  @Override
  public WebDriver getUnderlyingDriver() {
    return mapOfThreadedDrivers.get(Thread.currentThread().getId());
  }

  @Override
  public WebDriver confirmAndRetrieveDriver() {
    final WebDriver driver = mapOfThreadedDrivers.get(Thread.currentThread().getId());
    return driver != null ? driver : setWebDriver(createWebDriver());
  }

  @Override
  public WebDriver setWebDriver(WebDriver driver) {
    mapOfThreadedDrivers.put(Thread.currentThread().getId(), driver);
    return driver;
  }

    @Override
    public String getDomSource() {
        return getUnderlyingDriver().getPageSource();
    }

    @Override
    public void clearSession() {
         getUnderlyingDriver().manage().deleteAllCookies();
    }

    @Override
    public String getCurrentUrl() {
        return getUnderlyingDriver().getCurrentUrl();
    }

    private WebDriver createWebDriver() {
        return factory.createDriver(proxy);
  }

}
