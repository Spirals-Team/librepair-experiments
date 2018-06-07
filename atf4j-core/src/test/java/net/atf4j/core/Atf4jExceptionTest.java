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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A UnitTest for Atf4jException objects.
 */
public final class Atf4jExceptionTest extends TestResultsReporting {

    /** The Constant 			EXCEPTION_MESSAGE. */
    private static final String EXCEPTION_MESSAGE = "Force a Atf4jException";

    /**
     * test Atf4jException object.
     *
     * @throws VerificationError the atf4j exception
     */
    @Test(expected = VerificationError.class)
    public void testAtf4jException() throws VerificationError {
        throw new VerificationError();
    }

    /**
     * Test method for Atf4jException.
     *
     * @throws VerificationError the Atf4j exception
     */
    @Test(expected = VerificationError.class)
    public void testAtf4jExceptionString() throws VerificationError {
        try {
            throw new VerificationError(EXCEPTION_MESSAGE);
        } catch (final VerificationError exception) {
            log.error(exception.toString());
            assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
            assertEquals(EXCEPTION_MESSAGE, exception.getLocalizedMessage());
            throw exception;
        }
    }

    /**
     * Test method for Atf4jException.
     *
     * @throws VerificationError the Atf4j exception
     */
    @Test(expected = VerificationError.class)
    public void testAtf4jExceptionAssertion() throws VerificationError {
        try {
            throw new VerificationError(new AssertionError(EXCEPTION_MESSAGE));
        } catch (final VerificationError exception) {
            log.error(exception.toString());
            assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE));
            assertTrue(exception.getMessage().contains("java.lang.AssertionError"));
            throw exception;
        }
    }
}
