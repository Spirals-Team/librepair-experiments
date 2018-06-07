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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.atf4j.core.AbstractConfig.ConfigurationNotLoadedException;

/**
 * Web Site Steps, these are bindings from Gherkin to Java. <tt>
 *  Plurals     names?  matches name or names
 *              an?     matches a or an
 *  Quoted      '[^']*' matches "something" in single quotes
 *              "[^"]*" matches "something" in double quotes
 *  Bracket
 *  Wildcard    .*      matches anything 0-n times
 *              .+      matches anything 1-n times
 *  digit       [0-9]   matches a single digit
 *  number      [0-9]+  matches series of digits until none digit
 *              d+      matches series of digits
 *  word        [a-z]*  matches word until none letter
 *              d*      matches a series of digits (or nothing)
 * </tt>
 */
public class WebSiteSteps {

    /** The web site. */
    private final BehaviourModel webSite = new BehaviourModel();
    
    /** The log. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Named browser.
     */
    @Given("^a web browser is available$")
    public void namedBrowser() {
        webSite.aBrowser();
    }

    /**
     * Named browser.
     *
     * @param browserName the browser name
     */
    @Given("^the (?:Chrome|Firefox|Headless) Browser$")
    public void namedBrowser(final String browserName) {
        webSite.useBrowser(browserName);
    }

    /**
     * The target url is http is opened.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     *             exception
     */
    @When("^the target url is opened$")
    public void theTargetUrlIsHttpIsOpened() throws ConfigurationNotLoadedException {
        webSite.open();
    }

    /**
     * The target url is opened. \bhttp://\S+ \"([^\"]*)\"
     *
     * @param targetUrl the target url
     * @throws ConfigurationNotLoadedException the configuration not loaded
     *             exception
     */
    @When("^the target url http://\\S+ is opened$")
    public void theTargetUrlIsHttpIsOpened(final String targetUrl) throws ConfigurationNotLoadedException {
        webSite.open(targetUrl);
    }

    /**
     * The page title is.
     *
     * @param expectedPageTitle the expected page title
     */
    @Then("^the pageTitle is \"([^\"]*)\"$")
    public void thePageTitleIs(final String expectedPageTitle) {
        webSite.pageTitleShouldBe(expectedPageTitle);
    }

    /**
     * The cookie exists.
     *
     * @param cookieName the cookie name
     */
    @Then("^the cookie \"([^\"]*)\" exists$")
    public void theCookieExists(final String cookieName) {
        webSite.ensureCookieExists(cookieName);
    }

    /**
     * The cookie has value.
     *
     * @param cookieName the cookie name
     * @param cookieValue the cookie value
     */
    @Then("^the cookie \"([^\"]*)\" has value \"([^\"]*)\"$")
    public void theCookieHasValue(final String cookieName, final String cookieValue) {
        webSite.theCookieHasValue(cookieName);
    }

}
