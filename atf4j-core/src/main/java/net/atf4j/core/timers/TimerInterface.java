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

/**
 * Timer Interface.
 */
public interface TimerInterface {

    /**
     * start timer.
     *
     * @return the timer
     */
    public abstract TimerInterface start();

    /**
     * stop timer.
     *
     * @return the timer
     */
    public abstract TimerInterface stop();

    /**
     * Gets the start time.
     *
     * @return start time in milliseconds as long.
     */
    public abstract long getStartTime();

    /**
     * Gets the stop time.
     *
     * @return the stop time
     */
    public abstract long getStopTime();

    /**
     * Gets the elapsed time.
     *
     * @return time in milliseconds as long.
     */
    public abstract long getElapsedTime();

}
