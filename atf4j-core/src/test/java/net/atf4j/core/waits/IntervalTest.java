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

package net.atf4j.core.waits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.timers.Interval;
import net.atf4j.core.timers.IntervalInterface;

/**
 * A UnitTest for Interval objects.
 */
public class IntervalTest extends TestResultsReporting {

    /**
     * Test method for Interval.
     */
    @Test
    public void testInterval() {
        final IntervalInterface interval = new Interval();
        log.debug("interval = {}", interval);
        assertNotNull(interval);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testIntervalLong() {
        final long useTimePeriod = 1000;
        final IntervalInterface interval = new Interval(useTimePeriod);
        log.debug("interval = {}", interval);
        verifyNotNull(interval);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testInMilliSeconds() {
        final long timePeriod = 1000;
        final IntervalInterface inMilliSeconds = Interval.inMilliSeconds(timePeriod);
        log.info("inMilliSeconds = {}", inMilliSeconds);
        verifyNotNull(inMilliSeconds);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testInSeconds() {
        final long timePeriod = 1;
        final IntervalInterface inSeconds = Interval.inSeconds(timePeriod);
        log.info("inSeconds = {}", inSeconds);
        verifyNotNull(inSeconds);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testInMinutes() {
        final long timePeriod = 1;
        final IntervalInterface inMinutes = Interval.inMinutes(timePeriod);
        log.info("inMinutes = {}", inMinutes);
        verifyNotNull(inMinutes);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testInHours() {
        final long timePeriod = 1;
        final IntervalInterface inHours = Interval.inHours(timePeriod);
        log.info("inHours = {}", inHours);
        verifyNotNull(inHours);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testInDays() {
        final long timePeriod = 1;
        final IntervalInterface inDays = Interval.inDays(timePeriod);
        log.info("inDays = {}", inDays);
        verifyNotNull(inDays);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testAsLong() {
        final IntervalInterface interval = new Interval();
        verifyNotNull(interval);
        final long asLong = interval.asLong();
        log.info("asLong = {}", asLong);
        assertEquals(0, asLong);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testAsMilliSeconds() {
        final IntervalInterface interval = new Interval();
        verifyNotNull(interval);
        final long asMilliSeconds = interval.asMilliSeconds();
        log.info("asMilliSeconds = {}", asMilliSeconds);
        assertEquals(0, asMilliSeconds);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testAsSeconds() {
        final IntervalInterface interval = new Interval();
        verifyNotNull(interval);
        final long asSeconds = interval.asSeconds();
        log.info("asSeconds = {}", asSeconds);
        assertEquals(0, asSeconds);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testAsMinutes() {
        final IntervalInterface interval = new Interval();
        verifyNotNull(interval);
        final long asMinutes = interval.asMinutes();
        log.info("asMinutes = {}", asMinutes);
        assertEquals(0, asMinutes);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testAsHours() {
        final IntervalInterface interval = new Interval();
        verifyNotNull(interval);
        final long asHours = interval.asHours();
        log.info("asHours = {}", asHours);
        assertEquals(0, asHours);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testAsDays() {
        final IntervalInterface interval = new Interval();
        verifyNotNull(interval);
        final long asDays = interval.asDays();
        log.info("asDays = {}", asDays);
        assertEquals(0, asDays);
    }

    /**
     * Test method for Interval.
     */
    @Test
    public void testIntervalToString() {
        final IntervalInterface interval = new Interval();
        log.info("interval = {}", interval);
        verifyNotNull(interval);
    }

}
