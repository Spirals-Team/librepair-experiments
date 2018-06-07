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
import net.atf4j.core.VerificationError;

/**
 * The Class AbstractCodeGeneratorTest.
 */
public class JavaCodeGeneratorTest extends TestResultsReporting {

    /**
     * The Class JavaClassGenerator.
     */
    public class JavaCodeGenerator extends AbstractJavaClassGenerator {

        /**
         * Instantiates a new java class generator.
         */
        public JavaCodeGenerator() {
            super();
        }

        /**
         * Instantiates a new java class generator.
         *
         * @param templateFilename the template filename
         * @throws TemplateNotLoadedException the template not loaded exception
         */
        public JavaCodeGenerator(final String templateFilename) throws TemplateNotLoadedException {
            super(templateFilename);
        }
    }

    /**
     * Test abstract code generator.
     *
     * @throws VerificationError the Atf4jException.
     */
    @Test
    public void testAbstractCodeGenerator() throws VerificationError {
        final JavaCodeGenerator javaClassGenerator = new JavaCodeGenerator();
        verifyNotNull(javaClassGenerator);
        javaClassGenerator.setClassName("JavaClassOne");
        javaClassGenerator.addStringField("aString");
        javaClassGenerator.addBooleanField("aBoolean");
        javaClassGenerator.addObjectField("aObject");

        // javaClassGenerator.addField(ClassField.addNumber("aNumber"));
        // javaClassGenerator.addField(ClassField.addDate("aDate"));

        verifyNotNull(javaClassGenerator.generate());
    }

    /**
     * Test abstract code generator string.
     *
     * @throws VerificationError the Atf4jException.
     */
    @Test
    public void testAbstractCodeGeneratorString() throws VerificationError {
        final JavaCodeGenerator javaClassGenerator = new JavaCodeGenerator("templates/Class.vm");
        verifyNotNull(javaClassGenerator);
        javaClassGenerator.setClassName("JavaClassTwo");
        javaClassGenerator.addStringField("aString");
        javaClassGenerator.addBooleanField("aBoolean");
        javaClassGenerator.addObjectField("aObject");

        // javaClassGenerator.addField(ClassField.addNumber("aNumber"));
        // javaClassGenerator.addField(ClassField.addDate("aDate"));

        verifyNotNull(javaClassGenerator.generate());
    }

}
