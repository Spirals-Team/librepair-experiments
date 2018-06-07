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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for NanoTimer objects.
 */
public final class NanoTimerTest extends TestResultsReporting {

    /**
     * Test method for NanoTimer.
     */
    @Test
    public void testDefaultConstructor() {
        final NamedNanoTimer nanoTimer = new NamedNanoTimer();
        log.debug("nanoTimer = {}", nanoTimer);
        verifyNotNull(nanoTimer);
    }

    /**
     * Test method for NanoTimer.
     */
    @Test
    public void testNanoTimerString() {
        final String actual = "Named NanoTimer";
        final NamedNanoTimer nanoTimer = new NamedNanoTimer(actual);
        log.debug("nanoTimer = {}", nanoTimer);
        verifyNotNull(nanoTimer);
        assertEquals(actual, nanoTimer.getTimerName());
    }

    /**
     * Test method for NanoTimer.
     */
    @Test
    public void testStart() {
        final NamedNanoTimer nanoTimer = new NamedNanoTimer();
        log.debug("nanoTimer = {}", nanoTimer);
        verifyNotNull(nanoTimer.start());
    }

    /**
     * Test method for NanoTimer.
     */
    @Test
    public void testStop() {
        final NamedNanoTimer nanoTimer = new NamedNanoTimer();
        log.debug("nanoTimer = {}", nanoTimer);
        verifyNotNull(nanoTimer.stop());
    }

    /**
     * Test method for NanoTimer.
     */
    @Test
    public void testGetStartTime() {
        final NamedNanoTimer nanoTimer = new NamedNanoTimer();
        log.debug("nanoTimer = {}", nanoTimer);
        verifyNotNull(nanoTimer.getStartTime());
    }

    /**
     * Test method for NanoTimer.
     */
    @Test
    public void testGetElapsedTime() {
        final NamedNanoTimer nanoTimer = new NamedNanoTimer();
        log.debug("nanoTimer = {}", nanoTimer);
        verifyNotNull(nanoTimer.getElapsedTime());
    }

    /**
     * Test method for NanoTimer.
     */
    @Test
    public void testToString() {
        final NamedNanoTimer nanoTimer = new NamedNanoTimer();
        log.debug("nanoTimer = {}", nanoTimer);
        verifyNotNull(nanoTimer.toString());
    }

}
