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

package net.atf4j.webdriver.page;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import net.atf4j.core.AbstractConfig.ConfigurationNotLoadedException;
import net.atf4j.core.TestResultsReporting;

/**
 * Unit Test for abstract PageObject.
 */
@Ignore
public final class PageObjectTest extends TestResultsReporting {

    /**
     * Mock Page to extend AbstractPageObject.
     */
    public final class MockPage extends AbstractPageObject {

        /** The Constant TARGET_URL. */
        protected static final String TARGET_URL = "http://127.0.0.1:8080";

        /**
         * Instantiates a new mock page.
         *
         * @throws ConfigurationNotLoadedException the configuration not loaded
         */
        public MockPage() throws ConfigurationNotLoadedException {
            super();
        }

        /**
         * Instantiates a new mock page.
         *
         * @param webDriver the web driver
         */
        public MockPage(final WebDriver webDriver) {
            super(webDriver);
        }

        /**
         * Instantiates a new mock page.
         *
         * @param targetUrl the target url
         * @throws ConfigurationNotLoadedException the configuration not loaded
         */
        public MockPage(final String targetUrl) throws ConfigurationNotLoadedException {
            super(targetUrl);
        }
    }

    /**
     * Test method for {
     * net.atf4j.webdriver.page.AbstractPageObject#AbstractPageObject()}.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    @Test
    public void testDefaultConstructor() throws ConfigurationNotLoadedException {
        verifyNotNull(new MockPage());
    }

    /**
     * Test method for {
     * net.net.atf4j.webdriver.page.AbstractPageObject#open()}.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    @Test
    public void testDefaultOpen() throws ConfigurationNotLoadedException {
        final MockPage mockPage = new MockPage();
        assumeNotNull(mockPage);
        verifyNotNull(mockPage.open());
    }

    /**
     * Test method for {
     * net.net.atf4j.webdriver.page.AbstractPageObject#open()}.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    @Test
    public void testOpen() throws ConfigurationNotLoadedException {
        final MockPage mockPage = new MockPage(MockPage.TARGET_URL);
        assumeNotNull(mockPage);
        verifyNotNull(mockPage.open());
    }

    /**
     * Test method for {
     * net.net.atf4j.webdriver.page.AbstractPageObject#open(java.lang.String)}.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    @Test
    public void testOpenString() throws ConfigurationNotLoadedException {
        final MockPage mockPage = new MockPage();
        assumeNotNull(mockPage);
        verifyNotNull(mockPage.open());
    }

    /**
     * Test method for {
     * net.net.atf4j.webdriver.page.AbstractPageObject#verify()}.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    @Test
    public void testVerify() throws ConfigurationNotLoadedException {
        final MockPage mockPage = new MockPage();
        assumeNotNull(mockPage);
        verifyNotNull(mockPage.open());
        verifyNotNull(mockPage.verify());
    }

    /**
     * test PageObject object.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded
     */
    @Test
    public void testWebDriverConstructor() throws ConfigurationNotLoadedException {
        final MockPage mockPage = new MockPage(new HtmlUnitDriver());
        assumeNotNull(mockPage);
        verifyNotNull(mockPage.open());
        verifyNotNull(mockPage.verify());
    }

}
