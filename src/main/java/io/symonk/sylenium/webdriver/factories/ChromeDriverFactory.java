package io.symonk.sylenium.webdriver.factories;

import io.symonk.sylenium.SyConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.openqa.selenium.remote.BrowserType.CHROME;

/**
 * Implementation for chrome driver(s)
 */
@Slf4j
public class ChromeDriverFactory extends BaseDriverFactory {

    @Override
    boolean matchesConfiguration() {
        return SyConfig.$browser.equalsIgnoreCase(CHROME);
    }

    @Override
    WebDriver create(final Proxy proxy) {
        return new ChromeDriver(generateChromeOptions(proxy));
    }

    private ChromeOptions generateChromeOptions(final Proxy proxy) {
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.merge(generateSharedCapabilities(proxy));
        log.info("Chrome Options are: " + options.toString());
        return options;
    }

}
