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
 * BootStrapPage Page object.
 */
@TargetUrl("http://atf4j.net/bootstrap/")
public class BootstrapPage extends AbstractPageObject {

    /**
     * Instantiates a new bootstrap page.
     */
    public BootstrapPage() {
        super();
    }

    /**
     * Instantiates a new bootstrap page.
     *
     * @param targetUrl the target url
     */
    public BootstrapPage(final String targetUrl) {
        super(targetUrl);
    }

    /**
     * Instantiates a new bootstrap page.
     *
     * @param webDriver the web driver
     */
    public BootstrapPage(final WebDriver webDriver) {
        super(webDriver);
    }

}
