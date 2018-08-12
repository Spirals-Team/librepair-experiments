package io.symonk.sylenium.webdriver.factories;

import io.symonk.sylenium.webdriver.interfaces.DriverProvidable;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS;
import static org.openqa.selenium.remote.CapabilityType.PROXY;

/** Default abstract factory for instantiating drivers */
public abstract class BaseDriverFactory {

  /** check if the browser matches the value set for $browser in the config */
  abstract boolean matchesConfiguration();

  /** Create a new instance of a particular driver */
  abstract WebDriver create(final Proxy proxy);

  /** Instantiate a new driver instance using reflection */
  WebDriver instantiate(final String driverProviderClass, final Proxy proxy) {
    try {
      final DesiredCapabilities capabilities = generateSharedCapabilities(proxy);
      capabilities.setJavascriptEnabled(true);

      final Class<?> clazz = Class.forName(driverProviderClass);
      if (DriverProvidable.class.isAssignableFrom(clazz)) {
        final Constructor<?> driverConstructor = clazz.getDeclaredConstructor();
        driverConstructor.setAccessible(true);
        return ((DriverProvidable) driverConstructor.newInstance()).createDriver(capabilities);
      } else {
        final Constructor<?> other =
            Class.forName(driverProviderClass).getConstructor(Capabilities.class);
        return ((WebDriver) other.newInstance(capabilities));
      }
    } catch (final InvocationTargetException exception) {
      throw handleExceptions(exception.getTargetException());
    } catch (final Exception exception) {
      throw new IllegalArgumentException(exception);
    }
  }

  /** Generate a shared / common capabilities set that all drivers can use */
  DesiredCapabilities generateSharedCapabilities(final Proxy proxy) {
    final DesiredCapabilities capabilities = new DesiredCapabilities();
    if (proxy != null) capabilities.setCapability(PROXY, proxy);
    capabilities.setCapability(ACCEPT_SSL_CERTS, true);
    return capabilities;
  }

  /** Handle exceptions with reflection instantiation */
  private RuntimeException handleExceptions(final Throwable exception) {
    return exception instanceof RuntimeException
        ? (RuntimeException) exception
        : new RuntimeException(exception);
  }
}
