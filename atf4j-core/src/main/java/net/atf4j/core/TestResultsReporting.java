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

package net.atf4j.core;

import org.junit.After;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Results Reporting class.
 */
public abstract class TestResultsReporting {

    /** Unexpected null message. */
    protected static final String UNEXPECTED_NULL = "Unexpected null.";

    /** Expected exception message. */
    protected static final String EXPECTED_EXCEPTION = "Expected exception but did not occur.";

    /** logging */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * Instantiates a new test results reporting.
     */
    protected TestResultsReporting() {
        super();
    }

    /**
     * Verify object is not null.
     *
     * @param object the object
     */
    protected void verifyNotNull(final Object object) {
        if (object == null) {
            throw new VerificationError(UNEXPECTED_NULL);
        }
        log.trace("verifyNotNull({}) = {}", object.getClass().getSimpleName(), object.toString());
    }

    /**
     * Verify not null.
     *
     * @param message the message
     * @param object the object
     */
    protected void verifyNotNull(final String message, final Object object) {
        if (object == null) {
            throw new VerificationError(message);
        }
        log.trace("verifyNotNull({}) = ", object.getClass().getSimpleName(), object);
    }

    /**
     * To description.
     *
     * @return the string
     */
    protected String toDescription() {
        final StringBuffer message = new StringBuffer();
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (final StackTraceElement stackTraceElement : stackTrace) {
            final String candidateMethodName = stackTraceElement.getMethodName();
            message.append(candidateMethodName);
            message.append(' ');
        }
        return message.toString();
    }

    /**
     * Before.
     */
    @BeforeClass
    public static void beforeClass() {
        final String ruleoff = fillString(40, '=');
        LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME).trace(ruleoff);
    }

    /**
     * After.
     */
    @After
    public void after() {
        final String ruleoff = fillString(40, '-');
        log.trace(ruleoff);
    }

    /**
     * Fill string.
     *
     * @param length the length
     * @param chr the chr
     * @return the string
     */
    protected static String fillString(final int length, final char chr) {
        return fillString("", length, chr);
    }

    /**
     * Fill string.
     *
     * @param stem the stem
     * @param length the length
     * @param chr the chr
     * @return the string
     */
    protected static String fillString(final CharSequence stem, final int length, final char chr) {
        final StringBuilder stringBuilder = new StringBuilder(length);
        stringBuilder.append(stem);
        while (stringBuilder.length() < length) {
            stringBuilder.append(chr);
        }
        return stringBuilder.toString();
    }
}
