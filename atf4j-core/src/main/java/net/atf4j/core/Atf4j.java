/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with atf4j. If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.core;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atf4j.core.timers.NestedTimers;

/**
 * Atf4j class.
 */
public final class Atf4j {

    /** The Constant 			LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(Atf4j.class);
    
    /** The Constant 			nestedTimers. */
    private static final NestedTimers nestedTimers = NestedTimers.getInstance();

    /**
     * Instantiates a new atf4j.
     */
    private Atf4j() {
        super();
    }

    /**
     * Start.
     */
    public static void start() {
        start(UUID.randomUUID().toString());
    }

    /**
     * Start.
     *
     * @param string the string
     */
    public static void start(final String string) {
        LOG.info("start {}", string);
        nestedTimers.startTimer(string);
    }

    /**
     * End.
     */
    public static void end() {
        LOG.info("end {}", nestedTimers.stopTimer().toString());
    }

    /**
     * Document.
     */
    public static void document() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        document(stackTrace);
    }

    /**
     * Document.
     *
     * @param stackTrace the stack trace
     * @return the string
     */
    public static String document(final StackTraceElement[] stackTrace) {
        final StringBuffer document = new StringBuffer();
        for (final StackTraceElement stackTraceElement : stackTrace) {
            final String methodName = stackTraceElement.getMethodName();
            final String phrase = unroll(methodName);
            document.append(phrase);
            LOG.debug(phrase);
        }
        return document.toString();
    }

    /**
     * Unroll.
     *
     * @param input the input
     * @return the string
     */
    private static String unroll(final String input) {
        final StringBuffer phrase = new StringBuffer(input.length() * 2);
        if (input != null) {
            if (input.length() > 0) {
                for (int i = 0; i < input.length(); i++) {
                    final char chr = input.charAt(i);
                    if (Character.isUpperCase(chr)) {
                        phrase.append(' ');
                        phrase.append(Character.toLowerCase(chr));
                    }
                }
                return phrase.toString();
            }
        }
        phrase.setCharAt(0, Character.toUpperCase(input.charAt(0)));
        return phrase.toString();
    }

}
