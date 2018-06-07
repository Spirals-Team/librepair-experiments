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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.VerificationError;

/**
 * A UnitTest for TestCase objects.
 */
public class TestCaseTest extends TestResultsReporting {

    /**
     * The TestCaseReport Class.
     */
    public class MockTestReport implements TestReport {
    }

    /**
     * The PassingCondition Class.
     */
    public class PassingCondition extends Condition {
    }

    /**
     * Test method for TestCase.
     */
    @Test
    public void testDefaultConstructor() {
        verifyNotNull(new TestCase());
    }

    /**
     * Unit Test for test test case string.
     */
    @Test
    public void testTestCaseString() {
        verifyNotNull(new TestCase("TestCase"));
    }

    /**
     * Test method for TestCase.
     *
     * @throws VerificationError the Atf4jException
     */
    @Test
    public void testRegisterLogging() throws VerificationError {
        verifyNotNull(new TestCase().registerLogging(new MockTestReport()));
    }

    /**
     * Test method for TestCase.
     */
    @Test
    public void testNumberOfTestSteps() {
        final TestCase testCase = new TestCase();
        assertEquals(0, testCase.numberOfTestSteps());
    }

    /**
     * Test method for TestCase.
     */
    @Test
    public void testAddTestStep() {
        final TestStep testStep = new TestStep();
        verifyNotNull(testStep);

        final TestCase testCase = new TestCase();
        verifyNotNull(testCase);

        final TestCase addTestStep = testCase.addTestStep(testStep);
        verifyNotNull(testStep);

        assertEquals(1, addTestStep.numberOfTestSteps());
    }

    /**
     * Unit Test for test pre condition.
     */
    @Test
    public void testPreCondition() {
        final TestCase testCase = new TestCase();
        verifyNotNull(testCase.addPreCondition(new PassingCondition()));
    }

    /**
     * Unit Test for test post condition.
     */
    @Test
    public void testPostCondition() {
        final TestCase testCase = new TestCase();
        verifyNotNull(testCase.addPostCondition(new PassingCondition()));
    }

}
