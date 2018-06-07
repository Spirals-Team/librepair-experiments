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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for BoilerplatePage objects.
 */
@Ignore
public final class BoilerplatePageTest extends TestResultsReporting {

    private static final String PAGE_URL = "http://atf4j.net/boilerplate/";
    // private static final String PAGE_URL = "http://127.0.0.1:8080/bootstrap";

    /**
     * Unit Test for test boilerplate page.
     */
    @Test
    public void testBoilerplatePage() {
        final BoilerplatePage boilerplatePage = new BoilerplatePage();
        assumeNotNull(boilerplatePage);
        assertNotNull(boilerplatePage.open());
        assertNotNull(boilerplatePage.verify());
        assertNotNull(boilerplatePage.close());
    }

    /**
     * Unit Test for test boilerplate page url.
     */
    @Test
    public void testBoilerplateLoad() {
        final BoilerplatePage boilerplatePage = new BoilerplatePage();
        assumeNotNull(boilerplatePage);
        assertNotNull(boilerplatePage.open());
        assertNotNull(boilerplatePage.verify());
        assertNotNull(boilerplatePage.close());
    }

    /**
     * Unit Test for test boilerplate page url.
     */
    @Test
    public void testBoilerplatePageUrl() {
        final BoilerplatePage boilerplatePage = new BoilerplatePage(BoilerplatePageTest.PAGE_URL);
        assumeNotNull(boilerplatePage);
        assertNotNull(boilerplatePage.open());
        assertNotNull(boilerplatePage.verify());
        assertNotNull(boilerplatePage.close());
    }

    /**
     * Unit Test for test boilerplate page web driver.
     */
    @Test
    public void testBoilerplatePageWebDriver() {
        final HtmlUnitDriver webDriver = new HtmlUnitDriver();
        assumeNotNull(webDriver);
        final BoilerplatePage boilerplatePage = new BoilerplatePage(webDriver);
        assumeNotNull(boilerplatePage);
        assertNotNull(boilerplatePage.open());
        assertNotNull(boilerplatePage.verify());
        assertNotNull(boilerplatePage.close());
    }

}
