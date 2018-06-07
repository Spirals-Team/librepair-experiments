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

import java.util.Properties;
import java.util.UUID;

import net.atf4j.core.TestResultsReporting;

/**
 * Abstract class for a Hoare step. c.f. Hoare Logic
 */
public abstract class AbstractHoareStep
        extends TestResultsReporting
        implements HoareStepInterface {

    protected String id = UUID.randomUUID().toString();
    protected String stepName = "";
    protected Properties properties = new Properties();

    /**
     * Instantiates a new Hoare step.
     *
     * @param stepName the string
     */
    public AbstractHoareStep(final String stepName) {
        super();
        this.log.debug("AbstractHoareStep({})", stepName);
        this.stepName = stepName;
    }

    /**
     * Execute.
     *
     * @return the properties
     */
    public Properties execute() {
        this.log.debug("AbstractHoareStep({})", this.stepName);
        return this.properties;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.atf4j.fdd.HoareStepInterface#execute(java.util.Properties)
     */
    @Override
    public Properties execute(final Properties properties) {
        this.log.debug("AbstractHoareStep({}", this.stepName);
        this.properties = properties;
        return properties;
    }

}
