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

package net.atf4j.pog;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.pog.WebElementField.Strategy;

/**
 * A UnitTest for PageWebElement objects.
 */
public final class WebElementTest extends TestResultsReporting {

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testWebElementField() {
        final WebElementField pageWebElement = new WebElementField(Strategy.NAME, "locator", "byName");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testClassNameStrategy() {
        final WebElementField pageWebElement = new WebElementField(Strategy.CLASS_NAME,
                "class-name",
                "byClassName");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testLinkTextStrategy() {
        final WebElementField pageWebElement = new WebElementField(Strategy.LINK_TEXT,
                "linkText",
                "byLinkText");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testPartialLinkText() {
        final WebElementField pageWebElement = new WebElementField(Strategy.PARTIAL_LINK_TEXT,
                "partialLinkText",
                "byPartialLinkText");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testCssStrategy() {
        final WebElementField pageWebElement = new WebElementField(Strategy.CSS, "#", "byCss");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testXpathStrategy() {
        final WebElementField pageWebElement = new WebElementField(Strategy.XPATH, "//A", "byXpath");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testIdStrategy() {
        final WebElementField pageWebElement = new WebElementField(Strategy.ID, "id", "byId");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testNameStrategy() {
        final WebElementField pageWebElement = new WebElementField(Strategy.NAME, "name", "byName");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Unit tests for the PageWebElement object.
     */
    @Test
    public void testPageWebElementStringStrategyString() {
        final WebElementField pageWebElement = new WebElementField(Strategy.UNKNOWN, "unknown", "byUnknown");
        verifyNotNull(pageWebElement);
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

    /**
     * Test fluent interface.
     */
    @Test
    public void testFluentInterface() {
        final WebElementField pageWebElement = new WebElementField();
        verifyNotNull(pageWebElement);
        pageWebElement.setStrategy(Strategy.CLASS_NAME);
        pageWebElement.setLocator("class-name");
        pageWebElement.setFieldName("fieldName");
        log.debug("pageWebElement.debugString() = {}", pageWebElement.debugString());
        log.debug("pageWebElement.toCode() =\n{}", pageWebElement.toCode());
    }

}
