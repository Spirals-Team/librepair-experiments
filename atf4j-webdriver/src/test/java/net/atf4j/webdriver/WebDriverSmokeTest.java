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

import static org.junit.Assume.assumeTrue;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import net.atf4j.core.TestContext;
import net.atf4j.core.TestResultsReporting;

/**
 * Smoke Tests for WebDriver.
 */
public final class WebDriverSmokeTest extends TestResultsReporting {

    /**
     * Smoke test ChromeDriver.
     */
    @Test
    public void testChromeTomcat() {
        assumeTrue(TestContext.isLocal());
        log.info("webdriver.chrome.driver = {}",System.getProperty("webdriver.chrome.driver"));        
        final WebDriver webDriver = new ChromeDriver();
        verifyNotNull(webDriver);

        assumeTrue(TestContext.localServer());
        webDriver.get("http://127.0.0.1:8080/");
        final String pageTitle = webDriver.getTitle();
        verifyNotNull(webDriver);
        log.info("pageTitle={}", pageTitle);
    }

    /**
     * Smoke test FirefoxDriver.
     */
    @Test
    public void testFirefoxTomcat() {
        assumeTrue(TestContext.isLocal());
        log.info("webdriver.gecko.driver = {}",System.getProperty("webdriver.gecko.driver"));        
        final WebDriver webDriver = new FirefoxDriver();
        verifyNotNull(webDriver);

        assumeTrue(TestContext.localServer());
        webDriver.get("http://127.0.0.1:8080/");
        final String pageTitle = webDriver.getTitle();
        log.info("pageTitle={}", pageTitle);
    }

}
