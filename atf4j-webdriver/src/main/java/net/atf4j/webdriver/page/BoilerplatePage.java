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

import org.openqa.selenium.WebDriver;

import net.atf4j.webdriver.TargetUrl;

/**
 * Html5Boilerplate Page object..
 */
@TargetUrl("http://127.0.0.1:8080/boilerplate")
public class BoilerplatePage extends AbstractPageObject {

    /**
     * Instantiates a new Html5 Boilerplate page object.
     */
    public BoilerplatePage() {
        super();
    }

    /**
     * Instantiates a new Html5 Boilerplate page object.
     *
     * @param webDriver the web driver
     */
    public BoilerplatePage(final WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Instantiates a new Html5 Boilerplate page object.
     *
     * @param targetUrl the target url
     */
    public BoilerplatePage(final String targetUrl) {
        super(targetUrl);
    }

}
