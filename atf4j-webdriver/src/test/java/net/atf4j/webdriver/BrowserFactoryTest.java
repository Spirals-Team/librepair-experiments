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

import static org.junit.Assume.assumeNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for BrowserFactory objects.
 */
public final class BrowserFactoryTest extends TestResultsReporting {

    /**
     * test BrowserFactory returns Chrome.
     */
    @Test
    public void testChromeDriver() {
        System.setProperty("targetBrowser", "chromeDriver");
        final WebDriver webDriver = BrowserFactory.webDriver();
        assumeNotNull("Chrome webDriver not available", webDriver);
        verifyPageLoaded(webDriver);
    }

    /**
     * test BrowserFactory returns Firefox.
     */
    @Test
    public void testFirefoxDriver() {
        System.setProperty("targetBrowser", "firefoxDriver");
        final WebDriver webDriver = BrowserFactory.webDriver();
        assumeNotNull("Firefox webDriver not available", webDriver);
        verifyPageLoaded(webDriver);
    }

    /**
     * Unit Test for test remote driver.
     */
    @Test
    public void testRemoteDriver() {
        System.setProperty("targetBrowser", "remoteDriver");
        final WebDriver webDriver = BrowserFactory.webDriver();
        assumeNotNull("remote webDriver not available", webDriver);
        verifyPageLoaded(webDriver);
    }

    /**
     * test BrowserFactory default browser.
     */
    @Test
    public void testWebDriver() {
        final WebDriver webDriver = BrowserFactory.webDriver();
        assumeNotNull("default webDriver not available", webDriver);
        verifyPageLoaded(webDriver);
    }

    /**
     * Verify that verify page loaded.
     *
     * @param webDriver the web driver
     */
    private void verifyPageLoaded(final WebDriver webDriver) {
        webDriver.get("http://127.0.0.1:8080");
        final String currentUrl = webDriver.getCurrentUrl();
        log.info(currentUrl);
        final String pageTitle = webDriver.getTitle();
        log.info(pageTitle);
    }

}
