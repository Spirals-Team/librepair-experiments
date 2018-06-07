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

import java.util.Collection;

import org.junit.Test;

import net.atf4j.core.TestResult;
import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for TestResults objects.
 */
public class TestResultsTest extends TestResultsReporting {

    /**
     * Unit Test for test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        final TestResults testResults = new TestResults();
        verifyNotNull(testResults);
    }

    /**
     * Unit Test for test add test result.
     */
    @Test
    public void testAddTestResult() {
        final TestResults testResults = new TestResults();
        verifyNotNull(testResults);
        testResults.add(TestResult.PENDING);
    }

    /**
     * Unit Test for test test results.
     */
    @Test
    public void testTestResults() {
        final TestResults testResults = new TestResults();
        verifyNotNull(testResults);
        final Collection<TestResult> resultsCollection = testResults.getTestResults();
        verifyNotNull(resultsCollection);
    }

    /**
     * Unit Test for test to string.
     */
    @Test
    public void testToString() {
        verifyNotNull(new TestResults().toString());
    }

}
