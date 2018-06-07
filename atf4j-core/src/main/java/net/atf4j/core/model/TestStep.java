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

import net.atf4j.core.timers.MappedTimers;

/**
 * TestStep.
 */
public class TestStep extends AbstractTestBase {

    /**
     * Instantiates a new test step.
     */
    public TestStep() {
        super();
    }

    /**
     * Start test suite.
     *
     * @return the test case
     */
    public AbstractTestBase start() {
        log.info("start test step {}", this.getName());
        log.info("start timer {}", MappedTimers.start("TestStep"));
        super.assumedPreConditions();
        return this;
    }

    /**
     * End test suite.
     *
     * @return the test case
     */
    public AbstractTestBase end() {
        log.info("end timer {}", MappedTimers.stop("TestStep"));
        log.info("end test step {}", this.getName());
        super.assertPostConditions();
        return this;
    }

}
