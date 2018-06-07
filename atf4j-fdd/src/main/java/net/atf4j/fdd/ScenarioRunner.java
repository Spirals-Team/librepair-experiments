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

import java.lang.reflect.Method;

import net.atf4j.core.TestResultsReporting;

/**
 * Scenario Runner class.
 */
public final class ScenarioRunner extends TestResultsReporting {

    private AbstractScenario targetScenario;

    /**
     * Instantiates a new scenario runner.
     *
     * @param targetScenario the target scenario
     */
    public ScenarioRunner(final Object targetScenario) {
        super();
        setTargetScenario(targetScenario);
    }

    /**
     * Sets the target scenario.
     *
     * @param targetScenario the new target scenario
     */
    private void setTargetScenario(final Object targetScenario) {
        // if @Scenario Tag
        this.targetScenario = (AbstractScenario) targetScenario;
    }

    /**
     * Execute the scenario.
     *
     * @return the scenario runner
     */
    public ScenarioRunner execute() {
        return executeGiven().executeWhen().executeThen();
    }

    /**
     * Execute the scenario.
     *
     * @param scenario the scenario
     * @return the scenario runner
     */
    public ScenarioRunner execute(final AbstractScenario scenario) {
        this.targetScenario = scenario;
        return this;
    }

    /**
     * Execute given.
     *
     * @return the scenario runner
     */
    private ScenarioRunner executeGiven() {
        if (this.targetScenario != null) {
            return executeGiven(this.targetScenario);
        }
        return this;
    }

    /**
     * Execute given.
     *
     * @param scenario the scenario
     * @return the scenario runner
     */
    private ScenarioRunner executeGiven(final AbstractScenario scenario) {
        final Class<?> candidateClass = scenario.getClass();
        if (candidateClass.isAnnotationPresent(Feature.Scenario.class)) {
            this.log.trace(candidateClass.toGenericString());
            final Method[] declaredMethods = candidateClass.getDeclaredMethods();
            for (final Method method : declaredMethods) {
                if (method.isAnnotationPresent(Feature.Given.class)) {
                    this.log.debug(method.toGenericString());
                }
            }
        }
        return this;
    }

    /**
     * Execute when.
     *
     * @return the scenario runner
     */
    private ScenarioRunner executeWhen() {
        return executeWhen(this.targetScenario);
    }

    /**
     * Execute when.
     *
     * @param scenario the scenario
     * @return the scenario runner
     */
    private ScenarioRunner executeWhen(final AbstractScenario scenario) {
        final Class<?> candidateClass = scenario.getClass();
        if (candidateClass.isAnnotationPresent(Feature.Scenario.class)) {
            this.log.trace(candidateClass.toGenericString());
            final Method[] declaredMethods = candidateClass.getDeclaredMethods();
            for (final Method method : declaredMethods) {
                if (method.isAnnotationPresent(Feature.Given.class)) {
                    this.log.debug(method.toGenericString());
                }
            }
        }
        return this;
    }

    /**
     * Execute then.
     *
     * @return the scenario runner
     */
    private ScenarioRunner executeThen() {
        return executeThen(this.targetScenario);
    }

    /**
     * Execute then.
     *
     * @param scenario the scenario
     * @return the scenario runner
     */
    private ScenarioRunner executeThen(final AbstractScenario scenario) {
        final Class<?> candidateClass = scenario.getClass();
        if (candidateClass.isAnnotationPresent(Feature.Scenario.class)) {
            this.log.trace(candidateClass.toGenericString());
            final Method[] declaredMethods = candidateClass.getDeclaredMethods();
            for (final Method method : declaredMethods) {
                if (method.isAnnotationPresent(Feature.Given.class)) {
                    this.log.debug(method.toGenericString());
                }
            }
        }
        return this;
    }
}
