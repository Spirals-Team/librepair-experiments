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

/**
 * Result of a Test.
 */
public enum TestResult {

    /**
     * Test has not yet been run.
     */
    PENDING("Pending"),

    /**
     * Test was skipped because of a pre-condition was false.
     **/
    SKIPPED("Skipped"),

    /**
     * Test has been run and passed.
     */
    PASSED("Passed"),

    /**
     * Test has been run and the SUT failed.
     */
    FAILED("Failed"),

    /**
     * Test failed with an exception.
     */
    EXCEPTION("Exception");

    /** value. */
    private final String value;

    /**
     * Constructor for Test Status from string.
     *
     * @param asText the as text
     */
    private TestResult(final String asText) {
        value = asText;
    }

    /**
     * Initialise.
     *
     * @return the test result
     */
    public static TestResult initialise() {
        return TestResult.PENDING;
    }

    /**
     * TestResult from String value.
     *
     * @param string the as text
     * @return Status value
     */
    public static TestResult fromString(final String string) {
        for (final TestResult candidate : values()) {
            if (candidate.value.equals(string)) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * toString.
     *
     * @return Status value
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if is.
     *
     * @param result the result \* @return true, if successful, otherwise false.
     * @return true, if successful, otherwise false.
     */
    public boolean is(final TestResult result) {
        return this.equals(result);
    }

}
