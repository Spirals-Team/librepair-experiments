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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResult;
import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.VerificationError;

/**
 * A UnitTest for TestStep objects.
 */
public class TestStepTest extends TestResultsReporting {

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
     * Unit Test for test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        verifyNotNull(new TestStep());
    }

    /**
     * Unit Test for test test step.
     */
    @Test
    public void testTestStep() {
        final TestStep testStep = new TestStep();
        log.info("testStep={}", testStep);
        verifyNotNull(testStep);

        final TestResult result = testStep.result();
        log.info("testResult={}", result);
        verifyNotNull(result);
    }

    /**
     * Test method for TestCase.
     *
     * @throws VerificationError the Atf4jException
     */
    @Test
    public void testRegisterLogging() throws VerificationError {
        final TestCase testCase = new TestCase();
        log.info("testCase ={}", testCase);

        final MockTestReport report = new MockTestReport();
        log.info("report = {}", report);
        verifyNotNull(testCase.registerLogging(report));
    }

    /**
     * Unit Test for test pre condition.
     */
    @Test
    public void testPreCondition() {
        final TestStep testStep = new TestStep();
        log.info("testStep = {}", testStep);
        verifyNotNull(testStep);

        final PassingCondition preCondition = new PassingCondition();
        log.info("preCondition = {}", preCondition);
        verifyNotNull(preCondition);

        assertNotNull(testStep.addPreCondition(preCondition));
        log.info("testStep = {}", testStep);
    }

    /**
     * Unit Test for test post condition.
     */
    @Test
    public void testPostCondition() {
        final TestStep testStep = new TestStep();
        log.info("testStep = {}", testStep);
        verifyNotNull(testStep);

        final PassingCondition postCondition = new PassingCondition();
        log.info("postCondition = {}", postCondition);
        verifyNotNull(postCondition);

        assertNotNull(testStep.addPostCondition(postCondition));
        log.info("testStep = {}", testStep);
    }

}
