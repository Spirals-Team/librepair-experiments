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

package net.atf4j.core.model;

import java.util.ArrayDeque;
import java.util.Collection;

import net.atf4j.core.TestResult;
import net.atf4j.core.TestResultsReporting;

/**
 * TestResults.
 */
public class TestResults extends TestResultsReporting {

    private final Collection<TestResult> testResults = new ArrayDeque<TestResult>();

    /**
     * Add a TestResult to collection.
     *
     * @param testResult the test result \* @return true, if successful,
     *            otherwise false.
     * @return true, if successful, otherwise false.
     */
    public boolean add(final TestResult testResult) {
        return testResults.add(testResult);
    }

    /**
     * Gets the test results.
     *
     * @return the test results
     */
    public Collection<TestResult> getTestResults() {
        return testResults;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("TestResults [testResults=%s]", testResults);
    }

}
