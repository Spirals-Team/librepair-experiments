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

package net.atf4j.core.timers;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for MilliTimer objects.
 */
public final class NamedTimerTest extends TestResultsReporting {

    /**
     * Unit tests for the default constructor of NamedTimer object.
     */
    @Test
    public void testDefaultConstructor() {
        final NamedTimer namedTimer = new NamedTimer();
        verifyNotNull(namedTimer);
        log.debug("new NamedTimer() = {}", namedTimer);
    }

    /**
     * Test method for NamedTimer.
     */
    @Test
    public void testNamedTimerString() {
        final NamedTimer namedTimer = new NamedTimer("MilliTimer");
        verifyNotNull(namedTimer);
        log.debug("new NamedTimer() = {}", namedTimer);
    }

    /**
     * Test method for starting and stopping a NamedTimer.
     */
    @Test
    public void testStartStopNamedTimer() {
        final NamedTimer namedTimer = new NamedTimer();
        verifyNotNull(namedTimer);
        log.debug("namedTimer.start = {}", namedTimer.start());
        log.debug("namedTimer.stop = {}", namedTimer.stop());
    }

    /**
     * Test method for NamedTimer.
     */
    @Test
    public void testToString() {
        final NamedTimer namedTimer = new NamedTimer();
        verifyNotNull(namedTimer);
        log.debug("namedTimer.toString() = {}", namedTimer.toString());
    }

}
