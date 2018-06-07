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
 * Encapsulate an Interval of Time in a [GOF] Memento.
 */
public class Interval implements IntervalInterface {

    /** SECOND as 1000 Milliseconds. */
    public static final int SECOND = 1000;

    /** MINUTE as 60 Seconds. */
    public static final int MINUTE = 60 * SECOND;

    /** HOUR as 60 Minutes. */
    public static final int HOUR = 60 * MINUTE;

    /** DAY as 24 Hours. */
    public static final int DAY = 24 * HOUR;

    /** The time period. */
    public long timePeriod;

    /**
     * Instantiates a new interval.
     */
    public Interval() {
        super();
        this.timePeriod = 0L;
    }

    /**
     * Interval.
     *
     * @param useTimePeriod the use time period
     */
    public Interval(final long useTimePeriod) {
        super();
        this.timePeriod = useTimePeriod;
    }

    /**
     * factory method helper inMilliSeconds.
     *
     * @param timePeriod the use time period
     * @return new Interval object.
     */
    public static IntervalInterface inMilliSeconds(final long timePeriod) {
        return new Interval(timePeriod);
    }

    /**
     * inSeconds.
     *
     * @param timePeriod in Seconds.
     * @return new Interval object.
     */
    public static IntervalInterface inSeconds(final long timePeriod) {
        return new Interval(timePeriod * Interval.SECOND);
    }

    /**
     * inMinutes.
     *
     * @param timePeriod in Minutes.
     * @return new Interval object.
     */
    public static IntervalInterface inMinutes(final long timePeriod) {
        return new Interval(timePeriod * Interval.MINUTE);
    }

    /**
     * inHours.
     *
     * @param timePeriod in Hours.
     * @return new Interval object.
     */
    public static IntervalInterface inHours(final long timePeriod) {
        return new Interval(timePeriod * Interval.HOUR);
    }

    /**
     * inDays.
     *
     * @param timePeriod in Days.
     * @return new Interval object.
     */
    public static IntervalInterface inDays(final long timePeriod) {
        return new Interval(timePeriod * Interval.DAY);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.IntervalInterface#asMilliSeconds()
     */
    @Override
    public final long asMilliSeconds() {
        return this.timePeriod;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.IntervalInterface#asSeconds()
     */
    @Override
    public final long asSeconds() {
        return this.timePeriod / SECOND;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.IntervalInterface#asMinutes()
     */
    @Override
    public final long asMinutes() {
        return this.timePeriod / MINUTE;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.IntervalInterface#asHours()
     */
    @Override
    public final long asHours() {
        return this.timePeriod / HOUR;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.IntervalInterface#asDays()
     */
    @Override
    public final long asDays() {
        return this.timePeriod / DAY;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.core.timers.IntervalInterface#asLong()
     */
    @Override
    public final long asLong() {
        return this.timePeriod;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Interval [timePeriod=%s]", this.timePeriod);
    }

}
