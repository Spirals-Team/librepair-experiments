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

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for CriticalFail objects.
 */
public class CriticalFailTest extends TestResultsReporting {

    /**
     * Test method for CriticalFail.
     */
    @Test
    public void testCriticalFail() {
        final CriticalFail criticalFail = new CriticalFail();
        verifyNotNull(criticalFail);
    }

    /**
     * Test method for CriticalFail.
     */
    @Test
    public void testCriticalFailNull() {
        final CriticalFail criticalFail = new CriticalFail(null);
        verifyNotNull(criticalFail);
    }

}
