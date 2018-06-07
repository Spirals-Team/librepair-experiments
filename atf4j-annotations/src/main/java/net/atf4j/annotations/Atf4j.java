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

package net.atf4j.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Atf4j Annotation Interface.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Atf4j {

    /**
     * Automated Test Script Annotation Interface.
     */
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestScript {
    }

    /**
     * Test tag Annotation Interface.
     */
    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestTag {
        /**
         * Value of test tag.
         *
         * @return test tag as String.
         */
        String value();
    }

    /**
     * Test Id Annotation Interface.
     */
    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestId {
        /**
         * Value of test id.
         *
         * @return test id as String.
         */
        String value();
    }

    /**
     * Test Name Annotation Interface.
     */
    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestName {
        /**
         * Value of test name.
         *
         * @return the test name as String.
         */
        String value();
    }

    /**
     * Test Description Annotation Interface.
     */
    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestDescription {
        /**
         * Value of test description.
         *
         * @return the test description as String
         */
        String value();
    }

}
