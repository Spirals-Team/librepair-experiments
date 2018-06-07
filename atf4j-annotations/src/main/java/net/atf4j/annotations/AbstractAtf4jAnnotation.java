/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with atf4j. If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.annotations;

import net.atf4j.core.TestResultsReporting;

/**
 * An abstract class for test annotations of the Automated Testing framework.
 */
@Atf4j.TestScript
public abstract class AbstractAtf4jAnnotation extends TestResultsReporting {

    /**
     * Instantiates a new abstract annotated test.
     */
    protected AbstractAtf4jAnnotation() {
        super();
    }

    /**
     * Get the test tag.
     *
     * @return the test tag as a String object.
     */
    public String getTestTag() {
        return AnnotationHelper.getTestTag();
    }

    /**
     * Retrieve the test identifier from the annotation.
     *
     * @return the test id as a String object.
     */
    public String getTestId() {
        return AnnotationHelper.getTestId();
    }

    /**
     * Retrieve the test name from the annotation.
     *
     * @return the test name as a String.
     */
    public String getTestName() {
        return AnnotationHelper.getTestName();
    }

    /**
     * Retrieve the test description from the annotation.
     *
     * @return the test description as a String.
     */
    public String getTestDescription() {
        return AnnotationHelper.getTestDescription();
    }

}
