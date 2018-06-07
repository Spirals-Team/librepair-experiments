/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.webdriver;

import java.net.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.*;
import org.slf4j.*;

import net.atf4j.core.AbstractConfig.PropertyNotFoundException;
import net.atf4j.core.TestContext;

/**
 * A factory class for creating Browser objects.
 */
public class BrowserFactory implements BrowserFactoryInterface {

    private static final Logger log = LoggerFactory.getLogger(BrowserFactory.class);
    private static WebDriverConfig config;

    /**
     * Instantiates a new browser factory.
     */
    protected BrowserFactory() {
        super();
    }

    /**
     * Factory method for Selenium WebDriver.INSTANCE.
     *
     * @return webDriver
     */
    public static WebDriver webDriver() {
        if (BrowserFactory.config == null) {
            BrowserFactory.config = new BrowserFactorConfig();
        }
        return webDriver(BrowserFactory.config.targetBrowser());
    }

    /**
     * Factory method of webDriver INSTANCE.
     *
     * @param browser the browser
     * @return INSTANCE of webDriver
     */
    public static WebDriver webDriver(final String browser) {
        WebDriver webDriver = null;

        if (TestContext.isLocal()) {
            webDriver = localWebDriver(browser);
        } else if (TestContext.isGrid()) {
            webDriver = remoteWebDriver(browser);
        }

        initialiseWebDriver(webDriver);

        return webDriver;
    }

    /**
     * Initialise web driver.
     *
     * @param webDriver the web driver
     */
    private static void initialiseWebDriver(final WebDriver webDriver) {
        final Options manage = webDriver.manage();
        final Timeouts timeouts = manage.timeouts();
        timeouts.pageLoadTimeout(BrowserFactory.config.pageLoadTimeout(), TimeUnit.SECONDS);
        timeouts.implicitlyWait(BrowserFactory.config.implicitWait(), TimeUnit.SECONDS);
        timeouts.setScriptTimeout(BrowserFactory.config.scriptTimeout(), TimeUnit.MILLISECONDS);
    }

    /**
     * Local webDriver INSTANCE.
     *
     * @return the webDriver INSTANCE.
     */
    protected static WebDriver localWebDriver() {
        BrowserFactory.log.trace("localWebDriver()");
        final String targetBrowser = BrowserFactory.config.targetBrowser();
        return localWebDriver(targetBrowser);
    }

    /**
     * Local webDriver INSTANCE.
     *
     * @param targetBrowser the target browser
     * @return the webDriver
     */
    protected static WebDriver localWebDriver(final String targetBrowser) {
        WebDriver webDriver = null;

        switch (targetBrowser.toLowerCase()) {
            case "chrome":
                webDriver = new ChromeDriver();
                break;
            case "firefox":
                webDriver = new FirefoxDriver();
                break;
            case "ie":
                webDriver = new FirefoxDriver();
                break;
            default:
                webDriver = new HtmlUnitDriver();
                break;
        }

        return webDriver;
    }

    /**
     * Default remote webDriver INSTANCE.
     *
     * @return the web driver
     * @throws PropertyNotFoundException the property not found
     */
    protected static WebDriver remoteWebDriver() throws PropertyNotFoundException {
        final String targetBrowser = BrowserFactory.config.targetBrowser();
        return remoteWebDriver(targetBrowser);
    }

    /**
     * Named remote webDriver INSTANCE.
     *
     * @param targetBrowser the target browser
     * @return the web driver
     */
    protected static WebDriver remoteWebDriver(final String targetBrowser) {
        BrowserFactory.log.trace("remoteWebDriver(targetBrowser={})", targetBrowser);

        final DesiredCapabilities desiredCapabilities;

        switch (targetBrowser.toLowerCase()) {
            case "firefox":
                desiredCapabilities = DesiredCapabilities.firefox();
                break;
            case "chrome":
                desiredCapabilities = DesiredCapabilities.chrome();
                break;
            case "ie":
                desiredCapabilities = DesiredCapabilities.internetExplorer();
                break;
            default:
                desiredCapabilities = DesiredCapabilities.htmlUnit();
                break;
        }

        final String targetSeleniumGrid = BrowserFactory.config.seleniumUrl();
        final URL gridUrl;
        RemoteWebDriver remoteWebDriver = null;
        try {
            gridUrl = new URL(targetSeleniumGrid);
            remoteWebDriver = new RemoteWebDriver(gridUrl, desiredCapabilities);
        } catch (final MalformedURLException e) {
            BrowserFactory.log.error(e.toString());
        }
        return remoteWebDriver;
    }

}
