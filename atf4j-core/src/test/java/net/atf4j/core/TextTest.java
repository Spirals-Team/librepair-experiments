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

package net.atf4j.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * A Unit test class for Text objects.
 */
public final class TextTest extends TestResultsReporting {

    /**
     * Unit tests for the textFill() method of Text object.
     */
    @Test
    public void testTextFill() {
        final String fill = Text.fill(10);
        log.debug("Text.fill(10) = |{}|", fill);
        assertNotNull(fill);
        assertEquals(10, fill.length());
    }

    /**
     * Unit tests for the textFillStar() method of Text object.
     */
    @Test
    public void testTextFillStar() {
        final String fill = Text.fill(10, '*');
        log.debug("Text.fill(10, '*') = |{}|", fill);
        assertNotNull(fill);
        assertEquals(10, fill.length());
    }

    /**
     * Unit Test for test capitalise.
     */
    @Test
    public void testCapitalise() {
        assertEquals("", Text.capitalise(null));
        assertEquals("", Text.capitalise(""));
        assertEquals("Set", Text.capitalise("Set"));
        assertEquals("Set", Text.capitalise("Set"));
        assertEquals("Set.", Text.capitalise("sET."));
        assertEquals("Set.", Text.capitalise("sET."));
    }

    /**
     * Unit tests for the fillInt() method of Text object.
     */
    @Test
    public void testFillInt() {
        assertEquals("", Text.fill(0));
        assertEquals(" ", Text.fill(1));
        assertEquals("  ", Text.fill(2));
    }

    /**
     * Unit tests for the fillIntChar() method of Text object.
     */
    @Test
    public void testFillIntChar() {
        assertEquals("", Text.fill(0, '*'));
        assertEquals("*", Text.fill(1, '*'));
        assertEquals("**", Text.fill(2, '*'));
    }

    /**
     * Unit Test to pad a string to int length with spaces.
     */
    @Test
    public void testPadToLengthStringInt() {
        final String padToLength = Text.padToLength("Stem", 5);
        log.debug("Text.padToLength('Stem', 5) = |{}|", padToLength);
        assertEquals(5, padToLength.length());
        assertEquals("Stem ", padToLength);
    }

    /**
     * Unit Test to pad a string to int length with spaces character.
     */
    @Test
    public void testPadToLengthStringIntChar() {
        final String padToLength = Text.padToLength("Stem", 5, '*');
        log.debug("Text.padToLength('Stem', 5, '*') = |{}|", padToLength);
        assertEquals(5, padToLength.length());
        assertEquals("Stem*", padToLength);
    }
}
