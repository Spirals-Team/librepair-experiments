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
 * TestCase.
 */
public class TestCase extends AbstractTestBase {

    private final Collection<TestStep> testSteps = new ArrayDeque<TestStep>();

    /**
     * Instantiates a new test case.
     */
    public TestCase() {
        super();
    }

    /**
     * Instantiates a new test case with a name.
     *
     * @param name the name
     */
    public TestCase(final String name) {
        super(name);
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
    public TestCase execute(final TestContext context) throws VerificationError {
        assumeNotNull(context);
        assumeNotNull(testSteps);
        for (final TestStep testStep : testSteps) {
            final AbstractTestBase execute = testStep.execute(context);
            assumeNotNull(execute);
            assertEquals(testStep, execute);
        }
        return this;
    }

    /**
     * numberOfTestSteps.
     *
     * @return size of testStep collection as int.
     * @see java.util.Collection#size()
     */
    public int numberOfTestSteps() {
        return testSteps == null ? 0 : testSteps.size();
    }

    /**
     * Adds the test step.
     *
     * @param newTestStep as TestStep Object.
     * @return success as boolean.
     * @see java.util.Collection#add(java.lang.Object)
     */
    public TestCase addTestStep(final TestStep newTestStep) {
        final boolean result = testSteps.add(newTestStep);
        assumeTrue(result);
        return this;
    }

    /**
     * Start test suite.
     *
     * @return the test case
     */
    public TestCase start() {
        log.info("start test case {}", this.getName());
        log.info("start timer {}", MappedTimers.start("TestCase"));
        super.assumedPreConditions();
        return this;
    }

    /**
     * End test suite.
     *
     * @return the test case
     */
    public TestCase end() {
        log.info("end timer {}", MappedTimers.stop("TestCase"));
        log.info("end test case {}", this.getName());
        super.assertPostConditions();
        return this;
    }

}
