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

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.VerificationError;

/**
 * MappedTimers. [GOF] Singleton.
 *
 */
public final class MappedTimers extends TestResultsReporting {

    /** Single INSTANCE of this Class. */
    private static final MappedTimers MAPPED_TIMERS = new MappedTimers();

    /** The running timers. */
    private final Map<String, NamedTimer> runningTimers = new ConcurrentHashMap<String, NamedTimer>();

    /** The stopped timers. */
    private final Map<String, NamedTimer> stoppedTimers = new ConcurrentHashMap<String, NamedTimer>();

    /**
     * Private constructor to prevent wild instantiation.
     */
    private MappedTimers() {
        super();
    }

    /**
     * getInstance.
     *
     * @return TimerStack
     */
    public static MappedTimers getInstance() {
        return MappedTimers.MAPPED_TIMERS;
    }

    /**
     * Start.
     *
     * @param timerName the timer name
     * @return the i timer
     */
    public static TimerInterface start(final String timerName) {
        return getInstance().startTimer(timerName);
    }

    /**
     * Start timer.
     *
     * @param timerName the timer name
     * @return the i timer
     */
    public TimerInterface startTimer(final String timerName) {
        final NamedTimer timer = new NamedTimer(timerName);
        runningTimers.put(timerName, timer);
        timer.start();
        return timer;
    }

    /**
     * Stop.
     *
     * @param timerName the timer name
     * @return the i timer
     */
    public static TimerInterface stop(final String timerName) {
        return getInstance().stopTimer(timerName);
    }

    /**
     * stop the top Timer.
     *
     * @param timerName as String.
     * @return Timer.
     */
    public TimerInterface stopTimer(final String timerName) {
        final NamedTimer timer = runningTimers.get(timerName);
        if (timer != null) {
            timer.stop();
            stoppedTimers.put(timerName, timer);
            return timer;
        }
        return timer;
    }

    /**
     * stopAll.
     *
     * @return the mapped timers
     */
    public static MappedTimers stopAll() {
        return getInstance().stopAllTimers();
    }

    /**
     * stopAllTimers.
     *
     * @return the mapped timers
     */
    public MappedTimers stopAllTimers() {
        final Set<String> keys = runningTimers.keySet();
        for (final String key : keys) {
            stopTimer(key);
        }
        return this;
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public static String debugString() {
        return getInstance().toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [runningTimers=%s,stoppedTimers=%s]",
                this.getClass().getSimpleName(),
                runningTimers,
                stoppedTimers);
    }

    /**
     * The TimerNotFound Class.
     */
    @SuppressWarnings("serial")
    public class TimerNotFound extends VerificationError {
    }

}
