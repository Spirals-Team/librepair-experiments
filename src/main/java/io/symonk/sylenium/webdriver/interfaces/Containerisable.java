package io.symonk.sylenium.webdriver.interfaces;

import org.openqa.selenium.WebDriver;

public interface Containerisable {

    WebDriver getUnderlyingDriver();

    WebDriver confirmAndRetrieveDriver();

    WebDriver setWebDriver(final WebDriver driver);

    String getDomSource();

    void clearSession();

    String getCurrentUrl();
}
