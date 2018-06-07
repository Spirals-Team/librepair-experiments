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

package net.atf4j.fdd;

import org.junit.Ignore;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.fdd.Feature.Scenario;

/**
 * Scenario with a missing Given.
 */
@Scenario("Scenario")
public final class MissingGivenScenarioTest extends TestResultsReporting {

    /**
     * Test scenario runner.
     */
    @Ignore
    @Test
    public void testScenarioRunner() {
        this.log.debug("testScenarioRunner", this.getClass().getSimpleName());
        final ScenarioRunner scenarioRunner = new ScenarioRunner(this);
        verifyNotNull(scenarioRunner);
        verifyNotNull(scenarioRunner.execute());
    }

    // Deliberately missing.
    // @Atf4j.Given("Given")
    // public void testPassGiven() {
    // }

    /**
     * Test pass when.
     */
    @Feature.When("Skip When")
    public void skipWhen() {
        this.log.debug("{}", super.toString());
    }

    /**
     * Test pass then.
     */
    @Feature.Then("Skip Then")
    public void skipThen() {
        this.log.debug("{}", super.toString());
    }

}
