package io.symonk.sylenium.webdriver.factories;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

import static io.symonk.sylenium.SyConfig.$browser;

public class StandardDriverFactory extends BaseDriverFactory{
    @Override
    boolean matchesConfiguration() {
        return true;
    }

    @Override
    WebDriver create(Proxy proxy) {
        return instantiate($browser, proxy);
    }
}
