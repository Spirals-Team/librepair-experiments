/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.util;


/**
 * A utility class for performance statistics of Spoon.
 */
public class Timer {
    private static java.util.List<spoon.support.util.Timer> timestamps = new java.util.ArrayList<>();

    private static java.util.Deque<spoon.support.util.Timer> current = new java.util.ArrayDeque<>();

    /**
     * Starts a timer.
     *
     * @param name
     * 		the timer name
     */
    public static void start(java.lang.String name) {
        spoon.support.util.Timer.current.push(new spoon.support.util.Timer(name));
    }

    /**
     * Stops a timer.
     *
     * @param name
     * 		the timer name
     */
    public static void stop(java.lang.String name) {
        if (!(spoon.support.util.Timer.current.peek().getName().equals(name))) {
            throw new java.lang.RuntimeException("Must stop last timer");
        }
        spoon.support.util.Timer.current.peek().stop();
        spoon.support.util.Timer.timestamps.add(spoon.support.util.Timer.current.pop());
    }

    /**
     * Displays all the timers.
     */
    public static void display() {
        for (spoon.support.util.Timer time : spoon.support.util.Timer.timestamps) {
            java.lang.System.out.println(time);
        }
    }

    java.lang.String name;

    long start;

    long stop;

    /**
     * Constructs a timer.
     *
     * @param name
     * 		the timer name
     */
    public Timer(java.lang.String name) {
        super();
        this.name = name;
        start = java.lang.System.currentTimeMillis();
    }

    /**
     * Stops the current timer.
     */
    public void stop() {
        stop = java.lang.System.currentTimeMillis();
    }

    /**
     * Gets this timer's name.
     *
     * @return 
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Gets the current time of this timer.
     */
    public long getValue() {
        return (stop) - (start);
    }

    /**
     * A string representation of this timer.
     */
    @java.lang.Override
    public java.lang.String toString() {
        return (((getName()) + " \t") + (getValue())) + "ms";
    }
}

