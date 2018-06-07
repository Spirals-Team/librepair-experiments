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

package net.atf4j.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for Name objects.
 */
public final class NameTest extends TestResultsReporting {

    /**
     * The MockName Class.
     */
    public class MockName extends Name {

        /**
         * Instantiates a new mock name.
         */
        public MockName() {
            super();
        }

        /**
         * Instantiates a new mock name.
         *
         * @param name the name
         */
        public MockName(final String name) {
            super(name);
        }
    }

    /**
     * Test method for Name.
     */
    @Test
    public void testDefaultConstructor() {
        verifyNotNull(new MockName());
    }

    /**
     * Test method for Name.
     */
    public void testName() {
        verifyNotNull(new MockName());
    }

    /**
     * Test method for Name.
     */
    @Test
    public void testNameString() {
        final String expected = "Mock Name";
        final Name mockName = new MockName(expected);
        verifyNotNull(mockName);
        assertEquals(expected, mockName.getName());
    }

    /**
     * Test method for Name.
     */
    @Test
    public void testSetGetName() {
        final String expected = "Mock Name";
        final Name mockName = new MockName();
        assertEquals(expected, mockName.setName(expected).getName());
    }

    /**
     * Test method for Name.
     */
    @Test
    public void testCapitalise() {
        assertEquals("Name", Name.capitalise("nAME"));
    }

}
