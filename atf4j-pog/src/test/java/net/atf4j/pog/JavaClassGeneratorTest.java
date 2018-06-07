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

package net.atf4j.pog;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for JavaClassGenerator objects.
 */
public final class JavaClassGeneratorTest extends TestResultsReporting {

    /**
     * The JavaClassGenerator Class.
     */
    public class JavaClassGenerator extends AbstractJavaClassGenerator {

        /**
         * Instantiates a new java class generator.
         */
        public JavaClassGenerator() {
            super();
        }

        /**
         * Instantiates a new java class generator.
         *
         * @param templateFilename the template filename
         * @throws TemplateNotLoadedException the template not loaded
         */
        public JavaClassGenerator(final String templateFilename) throws TemplateNotLoadedException {
            super(templateFilename);
        }
    }

    /**
     * Test java class generator test.
     */
    @Test
    public void testJavaClassGeneratorTest() {
        final JavaClassGenerator javaClassGenerator = new JavaClassGenerator();
        verifyNotNull(javaClassGenerator);
        log.debug(javaClassGenerator.debugString());
        javaClassGenerator.generate();
    }

    /**
     * Unit tests for the javaClassGeneratorTestString() method of JavaClassGenerator object.
     */
    @Test
    public void testJavaClassGeneratorTestString() {
        final JavaClassGenerator javaClassGenerator = new JavaClassGenerator();
        verifyNotNull(javaClassGenerator);
        log.debug(javaClassGenerator.debugString());
        javaClassGenerator.generate();
    }

}
