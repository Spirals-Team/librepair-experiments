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
 * IntervalTester Class.
 */
public final class IntervalTest extends TestResultsReporting {

    private static final long DEFAULT_INTERVAL = 1000L;
    private static final long DELTA = 0L;

    /**
     * Test interval.
     */
    @Test
    public void testInterval() {
        final long asMilliSeconds = new Interval(DEFAULT_INTERVAL).asMilliSeconds();
        assertEquals(DEFAULT_INTERVAL, asMilliSeconds, DELTA);
    }

    /**
     * Test in milli seconds.
     */
    @Test
    public void testInMilliSeconds() {
        final long asMilliSeconds = Interval.inMilliSeconds(DEFAULT_INTERVAL).asMilliSeconds();
        assertEquals(DEFAULT_INTERVAL, asMilliSeconds, DELTA);
    }

    /**
     * Test in seconds.
     */
    @Test
    public void testInSeconds() {
        final long asMilliSeconds = Interval.inSeconds(1).asMilliSeconds();
        assertEquals(DEFAULT_INTERVAL * 1, asMilliSeconds, DELTA);
    }

    /**
     * Test in minutes.
     */
    @Test
    public void testInMinutes() {
        final long asMilliSeconds = Interval.inMinutes(1).asMilliSeconds();
        assertEquals(DEFAULT_INTERVAL * 60, asMilliSeconds, DELTA);
    }

    /**
     * Test in hours.
     */
    @Test
    public void testInHours() {
        final long asMilliSeconds = Interval.inHours(1).asMilliSeconds();
        assertEquals(DEFAULT_INTERVAL * 60 * 60, asMilliSeconds, DELTA);
    }

    /**
     * Test in days.
     */
    @Test
    public void testInDays() {
        final long asMilliSeconds = Interval.inDays(1).asMilliSeconds();
        assertEquals(DEFAULT_INTERVAL * 60 * 60 * 24, asMilliSeconds, DELTA);
    }

    /**
     * Test as milli seconds.
     */
    @Test
    public void testAsMilliSeconds() {
        final long asMilliSeconds = new Interval(DEFAULT_INTERVAL).asMilliSeconds();
        assertEquals(DEFAULT_INTERVAL, asMilliSeconds, DELTA);
    }

    /**
     * Test as long.
     */
    @Test
    public void testAsLong() {
        final long asLong = new Interval(DEFAULT_INTERVAL).asLong();
        assertEquals(DEFAULT_INTERVAL, asLong, DELTA);
    }

}
