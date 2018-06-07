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

import org.openqa.selenium.WebDriver;

import net.atf4j.core.AbstractConfig.ConfigurationNotLoadedException;
import net.atf4j.webdriver.page.AbstractPageObject;

/**
 * PageObject representing Landing.
 */
public class LandingPage extends AbstractPageObject {

    /**
     * Instantiates a new landing page.
     *
     * @throws ConfigurationNotLoadedException the configuration not loaded.
     */
    public LandingPage() throws ConfigurationNotLoadedException {
        super();
    }

    /**
     * Instantiates a new landing page.
     *
     * @param targetUrl the target url
     * @throws ConfigurationNotLoadedException the configuration not loaded.
     */
    public LandingPage(final String targetUrl) throws ConfigurationNotLoadedException {
        super(targetUrl);
    }

    /**
     * Instantiates a new landing page.
     *
     * @param webDriver the web driver
     */
    public LandingPage(final WebDriver webDriver) {
        super(webDriver);
    }

}
