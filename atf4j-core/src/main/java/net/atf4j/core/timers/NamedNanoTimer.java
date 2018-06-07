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

import java.util.UUID;

/**
 * A named nano second Timer. (In most cases this offers no advantage over the
 * millisecond timer).
 */
public class NamedNanoTimer implements TimerInterface {

    /** The start time. */
    private long startTime = -1L;

    /** The stop time. */
    private long stopTime = -1L;

    /** The elapsed time. */
    private long elapsedTime = -1L;

    /** The timer name. */
    private final String timerName;

    /**
     * Instantiates a new timer.
     */
    public NamedNanoTimer() {
        super();
        timerName = UUID.randomUUID().toString();
    }

    /**
     * Instantiates a new named timer.
     *
     * @param useName is a name for this Timer
     */
    public NamedNanoTimer(final String useName) {
        super();
        timerName = useName;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.ITimer#start()
     */
    @Override
    public final NamedNanoTimer start() {
        startTime = System.nanoTime();
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.ITimer#stop()
     */
    @Override
    public final NamedNanoTimer stop() {
        stopTime = System.nanoTime();
        elapsedTime = stopTime - startTime;
        return this;
    }

    /**
     * Gets the timer name.
     *
     * @return the timer name
     */
    public String getTimerName() {
        return timerName;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.ITimer#getStartTime()
     */
    @Override
    public final long getStartTime() {
        return startTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.TimerInterface#getStopTime()
     */
    @Override
    public long getStopTime() {
        return stopTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.ITimer#getElapsedTime()
     */
    @Override
    public long getElapsedTime() {
        return elapsedTime;
    }

}
