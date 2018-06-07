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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Verification class provides a number of static helper methods.
 * <p>
 * These are similar to Assertion in operation, but include results logging as
 * evidence of success.
 */
public final class Verify {

    /** Constant MESSAGE. */
    private static final String MESSAGE = "verify(expected(%s) == actual(%s)) - %s";

    /** LOG constant. */
    private static final Logger LOG = LoggerFactory.getLogger(Verify.class);

    /**
     * Instantiates a new verify.
     */
    private Verify() {
        super();
    }

    /**
     * Verify object is not null.
     *
     * @param message the message
     * @param object the object
     */
    public static void verifyNotNull(final String message, Object object) {
        if (object == null) {
            throw new VerificationError(message);
        }
        final String objectName = object.getClass().getSimpleName();
        verifyNotNull(objectName);
        LOG.debug("verify({}) = {}", objectName, object);
    }

    /**
     * Verify not null.
     *
     * @param object the object
     */
    public static void verifyNotNull(Object object) {
        assertNotNull(object);
        final String objectName = object.getClass().getSimpleName();
        assertNotNull(objectName);
        LOG.debug("verify({}) = {}", objectName, object);
    }

    /**
     * Verify two bytes are equal value.
     *
     * @param expected the expected
     * @param actual the actual
     * @return the string
     */
    public static String verifyEqual(final byte expected, final byte actual) {
        final boolean result = (actual == expected);
        final String message = resultMessage(expected, actual, result);
        LOG.debug("message = {}", message);
        assertEquals(message, expected, actual);
        return message;
    }

    /**
     * Verify two char are equal values.
     *
     * @param expected the expected
     * @param actual the actual
     * @return the string
     */
    public static String verifyEqual(final char expected, final char actual) {
        final boolean result = (actual == expected);
        final String message = resultMessage(expected, actual, result);
        LOG.debug("message = {}", message);
        assertEquals(message, expected, actual);
        return message;
    }

    /**
     * Verify two native integers are equal value.
     *
     * @param expected the expected
     * @param actual the actual
     * @return the string
     */
    public static String verifyEqual(final int expected, final int actual) {
        final boolean result = (actual == expected);
        final String message = resultMessage(expected, actual, result);
        LOG.debug("message = {}", message);
        assertEquals(message, expected, actual);
        return message;
    }

    /**
     * Verify two native longs are equal value.
     *
     * @param expected the expected as long.
     * @param actual the actual as long.
     * @return the messastring
     */
    public static String verifyEqual(final long expected, final long actual) {
        final boolean result = (actual == expected);
        final String message = resultMessage(expected, actual, result);
        LOG.debug("message = {}", message);
        assertEquals(message, expected, actual);
        return message;
    }

    /**
     * Verify two objects are equal.
     *
     * @param expected the expected
     * @param actual the actual
     * @return the string
     */
    public static String verifyEqual(final Object expected, final Object actual) {
        assertNotNull("Expected Value cannot be null", expected);
        assertNotNull("Actual Value cannot be null", actual);
        final boolean result = actual.equals(expected);
        final String message = resultMessage(expected, actual, result);
        LOG.debug("message = {}", message);
        assertEquals(message, expected, actual);
        return message;
    }

    /**
     * Format the result message.
     *
     * @param expected the expected value.
     * @param actual the actual value.
     * @param result the result of comparison.
     * @return the result message as a String.
     */
    private static String resultMessage(final Object expected, final Object actual, final boolean result) {
        return String.format(MESSAGE, expected, actual, resultString(result));
    }

    /**
     * Result as a string.
     *
     * @param result the result as a boolean.
     * @return the result as a String object.
     */
    private static String resultString(final boolean result) {
        return result ? "PASSED" : "FAILED";
    }

}
