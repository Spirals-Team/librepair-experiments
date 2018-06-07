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
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayDeque;
import java.util.Collection;

import net.atf4j.core.TestContext;
import net.atf4j.core.VerificationError;
import net.atf4j.core.timers.MappedTimers;

/**
 * TestSuite.
 */
public class TestSuite extends AbstractTestBase {

    protected Collection<TestCase> testCases;

    /**
     * Instantiates a new test suite.
     */
    public TestSuite() {
        super();
    }

    /**
     * Execute.
     *
     * @param context the context
     * @return the abstract test result
     * @throws VerificationError the atf4j exception
     * @see net.atf4j.core.model.AbstractTestBase#execute(net.atf4j.core.TestContext)
     */
    @Override
    public TestSuite execute(final TestContext context) throws VerificationError {
        assumeNotNull(context);
        assumeNotNull(testCases);
        for (final TestCase testCase : testCases) {
            assertEquals(testCase, testCase.execute(context));
        }
        return this;
    }

    /**
     * numberOfTestCases.
     *
     * @return size of testStep collection as int.
     * @see java.util.Collection#size()
     */
    public int numberOfTestCases() {
        return testCases == null ? 0 : testCases.size();
    }

    /**
     * Adds the test case.
     *
     * @param newTestCase as TestCase
     * @return success as boolean.
     * @see java.util.Collection#add(java.lang.Object)
     */
    public TestSuite addTestCase(final TestCase newTestCase) {
        if (testCases == null) {
            testCases = new ArrayDeque<TestCase>();
        }
        assumeNotNull(newTestCase);
        assumeTrue(testCases.add(newTestCase));
        return this;
    }

    /**
     * Start test suite.
     *
     * @return the test suite
     */
    public TestSuite start() {
        log.info("start test suite {}", this.getName());
        log.info("start timer {}", MappedTimers.start("TestSuite"));
        return this;
    }

    /**
     * End test suite.
     *
     * @return the test suite
     */
    public TestSuite end() {
        log.info("end timer {}", MappedTimers.stop("TestSuite"));
        log.info("end test suite {}", this.getName());
        return this;
    }

}
