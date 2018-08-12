package io.symonk.sylenium.webdriver.interfaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverProvidable {

  WebDriver createDriver(final DesiredCapabilities capabilities);
}
