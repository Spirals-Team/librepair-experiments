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

import java.util.List;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.pog.WebElementField.Strategy;

/**
 * A UnitTest for PageObjectData objects.
 */
public final class PageObjectDataTest extends TestResultsReporting {

    /**
     * Test method for void.
     */
    @Test
    public void testExpected() {
        final PageObjectData pageObjectData = new PageObjectData();
        verifyNotNull(pageObjectData);

        pageObjectData
            .add(new WebElementField(Strategy.ID, "id", "findById"))
            .add(new WebElementField(Strategy.CLASS_NAME, "class-name", "findByClassName"))
            .add(new WebElementField(Strategy.LINK_TEXT, "linkText", "findByLinkText"))
            .add(new WebElementField(Strategy.PARTIAL_LINK_TEXT, "partialLinkText", "findByPartialLinkText"))
            .add(new WebElementField(Strategy.NAME, "name", "findByName"))
            .add(new WebElementField(Strategy.XPATH, "//A", "findByXpath"))
            .add(new WebElementField(Strategy.CSS, ".", "findByCss"));

        logAttributes(pageObjectData);
    }

    /**
     * Test page object data.
     */
    @Test
    public void testPageObjectData() {
        final PageObjectData pageObjectData = new PageObjectData();
        verifyNotNull(pageObjectData);
    }

    /**
     * Log attributes.
     *
     * @param pageObjectData the page object data.
     */
    private void logAttributes(final PageObjectData pageObjectData) {
        final List<WebElementField> attributes = pageObjectData.get();
        for (final WebElementField pageWebElement : attributes) {
            verifyNotNull(pageWebElement);
        }
    }
}
