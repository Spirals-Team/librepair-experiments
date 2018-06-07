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

package net.atf4j.imperative;

import static org.junit.Assert.fail;

import org.openqa.selenium.WebDriver;

import net.atf4j.bdd.AbstractBehaviourModel;
import net.atf4j.core.AbstractConfig.ConfigurationNotLoadedException;
import net.atf4j.webdriver.BrowserFactory;

/**
 * A UnitTest for SystemUnder objects.
 */
public class BehaviourModel extends AbstractBehaviourModel {

    /** The web driver. */
    protected WebDriver webDriver;
    
    /** The target url. */
    protected String targetUrl = "http://127.0.0.1:8080";
    
    /** The landing page. */
    protected LandingPage landingPage;

    /**
     * A browser.
     */
    public void aBrowser() {
        log.debug("aBrowser()");
        webDriver = BrowserFactory.webDriver();
    }

    /**
     * Use browser.
     *
     * @param browserName the browser name
     */
    public void useBrowser(final String browserName) {
        log.debug("useBrowser({})", browserName);
        webDriver = BrowserFactory.webDriver(browserName);
    }

    /**
     * Open default landing page.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    public void open() throws ConfigurationNotLoadedException {
        log.debug("open()");
        landingPage = new LandingPage();
        landingPage.open();
    }

    /**
     * Open the target page.
     *
     * @param targetUrl the target url
     * @throws ConfigurationNotLoadedException the configuration not loaded
     *             exception
     */
    public void open(final String targetUrl) throws ConfigurationNotLoadedException {
        log.debug("open({})", targetUrl);
        landingPage = new LandingPage();
        landingPage.open(targetUrl);
    }

    /**
     * Verify.
     */
    public void verify() {
        log.debug("verify()");
        landingPage.verify();
    }

    /**
     * Page title should be.
     *
     * @param expectedPageTitle the expected page title
     */
    public void pageTitleShouldBe(final String expectedPageTitle) {
        log.debug("pageTitleShouldBe({})", expectedPageTitle);
        landingPage.verifyPageTitle(expectedPageTitle);
    }

    /**
     * Ensure cookie exists.
     *
     * @param expectedCookieName the expected cookie name
     */
    public void ensureCookieExists(final String expectedCookieName) {
        log.debug("ensureCookieExists({})", expectedCookieName);
        fail("Not yet implemented.");
    }

    /**
     * The cookie has value.
     *
     * @param expectedCookieName the expected cookie name
     */
    public void theCookieHasValue(final String expectedCookieName) {
        log.debug("theCookieHasValue({})", this, expectedCookieName);
        fail("Not yet implemented.");

    }

}
