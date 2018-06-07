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

package net.atf4j.core.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for Condition objects.
 */
public class ConditionTest extends TestResultsReporting {

    /**
     * MockTrue Class.
     */
    public class MockTrue extends Condition {
    }

    /**
     * MockFalse Class.
     */
    public class MockFalse extends Condition {
    }

    /**
     * Test method for Condition.
     */
    @Test
    public void testTrueCondition() {
        assertTrue(new MockTrue().isTrue());
    }

    /**
     * Test method for Condition.
     */
    @Test
    public void testFalseCondition() {
        assertFalse(new MockFalse().isFalse());
    }

}
