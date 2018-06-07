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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for NestedTimers objects.
 */
public final class NestedTimersTest extends TestResultsReporting {

    private static final int DEFAULT_INTERVAL = 100;

    /**
     * Test method for NestedTimers.
     */
    @Test
    public void testGetInstance() {
        final NestedTimers nestedTimers = NestedTimers.getInstance();
        log.debug("NestedTimers.getInstance() = {}", nestedTimers);
        verifyNotNull(nestedTimers);
    }

    /**
     * test NestedTimers.
     */
    @Test
    public void testNestedTimers() {
        final NestedTimers nestedTimers = NestedTimers.getInstance();
        log.debug("NestedTimers.getInstance() = {}", nestedTimers);
        verifyNotNull(nestedTimers);

        for (int i = 0; i < 10; i++) {
            final String message = String.format("Test case Timer %s", i);
            final TimerInterface startTimer = nestedTimers.startTimer(message);
            log.debug("startTimer = ", startTimer);
        }

        for (int i = 0; i < 10; i++) {
            final TimerInterface stopTimer = nestedTimers.stopTimer();
            log.debug("stopTimer = ", stopTimer);
        }
    }

    /**
     * Test method for
     * {net.atf4j.core.timers.MilliTimer#Timer(java.lang.String)}.
     */
    @Test
    public void testTimerInstance() {
        final NestedTimers nestedTimers = NestedTimers.getInstance();
        log.debug("NestedTimers.getInstance() = {}", nestedTimers);
        verifyNotNull(nestedTimers);

        nestedTimers.startTimer("Timer Instance : Test case 1");
        waitDefaultInterval();

        nestedTimers.startTimer("Timer Instance : Test case 2");
        waitDefaultInterval();

        nestedTimers.startTimer("Timer Instance : Test case 3");
        waitDefaultInterval();

        nestedTimers.startTimer("Timer Instance : Test case 4");
        waitDefaultInterval();

        nestedTimers.startTimer("Timer Instance : Test case 5");
        waitDefaultInterval();

        log.debug("runningTimersAsString = {}", nestedTimers.runningTimersAsString());

        assertNotNull(NestedTimers.stopAll());

        log.debug("stoppedTimersAsString() = {}", nestedTimers.stoppedTimersAsString());
    }

    /**
     * Wait default interval.
     */
    private void waitDefaultInterval() {
        try {
            Thread.sleep(DEFAULT_INTERVAL);
        } catch (final InterruptedException interruptedException) {
            final Thread currentThread = Thread.currentThread();
            currentThread.interrupt();
            log.error(interruptedException.toString());
        }
    }

    /**
     * Unit Test for test expected usage.
     */
    @Test
    public void testStaticExpectedUsage() {
        verifyNotNull(NestedTimers.start());
        verifyNotNull(NestedTimers.stop());
    }

    /**
     * Test method for
     * {net.atf4j.core.timers.MilliTimer#Timer(java.lang.String)}.
     */
    @Test
    public void testStaticNamedTimers() {
        assertNotNull(NestedTimers.start());
        waitDefaultInterval();

        assertNotNull(NestedTimers.start());
        waitDefaultInterval();

        assertNotNull(NestedTimers.start());
        waitDefaultInterval();

        assertNotNull(NestedTimers.start());
        waitDefaultInterval();

        assertNotNull(NestedTimers.start());
        waitDefaultInterval();

        assertNotNull(NestedTimers.stopAll());
    }

}
