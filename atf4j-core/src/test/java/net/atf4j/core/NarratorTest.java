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

package net.atf4j.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A UnitTest for Narrator objects.
 */
public final class NarratorTest extends TestResultsReporting {

    /**
     * The Class ClassUnderTest.
     */
    public final class ClassUnderTest {

        /** The object. */
        private final Object object = new Object();

        /** The class name. */
        private final String className = this.getClass().getSimpleName();

        /** The a boolean. */
        private final Boolean aBoolean = true;

        /** The number. */
        private final long number = Long.MAX_VALUE;

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return String.format("NarratorTest [object=%s, className=%s, aBoolean=%s, number=%s]",
                    object,
                    className,
                    aBoolean,
                    number);
        }
    }

    /**
     * SubClassWithoutProperties Class.
     */
    public class SubClassWithoutProperties {

        /*
         * (non-Javadoc)
         *
         * @see net.atf4j.core.StringBuilderTest#toString()
         */
        @Override
        public String toString() {
            return String.format("%s", this.getClass().getSimpleName());
        }
    }

    /**
     * The Class SubClassWithProperties.
     */
    public class SubClassWithProperties {

        /** Constant String. */
        private static final String CONSTANT = "CONSTANT";

        /** Constant π. */
        public static final double π = 3.14159;

        /** Protected string. */
        protected String protectedString = "protectedString";

        /** public string. */
        public String publicString = "publicString";

        /*
         * (non-Javadoc)
         *
         * @see net.atf4j.core.StringBuilderTest#toString()
         */
        @Override
        public String toString() {
            return String.format("%s [%s, %s, protectedString = '%s', publicString = '%s']",
                    this.getClass().getSimpleName(),
                    CONSTANT,
                    π,
                    protectedString,
                    publicString);
        }
    }

    /**
     * Unit tests for the expectedUsage() method of StringBuilder object.
     */
    @Test
    public void testExpectedUsage() {
        final String reflectObjectToString = Narrator.reflectObjectToString(getClass());
        log.info(reflectObjectToString);
    }

    /**
     * Test null class.
     */
    @Test
    public void testNullClass() {
        final String reflectionToString = Narrator.reflectObjectToString(null);
        assertEquals("object is [NULL]", reflectionToString);
        log.debug(reflectionToString);
    }

    /**
     * Test this class.
     */
    @Test
    public void testThisClass() {
        final String reflectionToString = Narrator.reflectObjectToString(this);
        assertNotNull(reflectionToString);
        log.debug(reflectionToString);
    }

    /**
     * Test sub class without properties.
     */
    @Test
    public void testSubClassWithoutProperties() {
        final SubClassWithoutProperties superClassWithoutProperties = new SubClassWithoutProperties();
        final String reflectionToString = Narrator.reflectObjectToString(superClassWithoutProperties);
        assertNotNull(reflectionToString);
        log.debug(reflectionToString);
    }

    /**
     * Test sub class with properties.
     */
    @Test
    public void testSubClassWithProperties() {
        final SubClassWithProperties superClassWithProperties = new SubClassWithProperties();
        final String reflectionToString = Narrator.reflectObjectToString(superClassWithProperties);
        assertNotNull(reflectionToString);
        log.debug(reflectionToString);
    }

    /**
     * Unit Test for test reflect object to string.
     */
    @Test
    public void testReflectObjectToString() {
        final ClassUnderTest object = new ClassUnderTest();
        assertNotNull(object);

        final String string = object.toString();
        assertNotNull(object);
        log.info("object.toString = {}", string);

        final String narration = Narrator.reflectObjectToString(object);
        assertNotNull(narration);
        log.info("narration = {}", narration);
    }

}
