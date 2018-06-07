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
 * A UnitTest for ClassMethod objects.
 */
public final class ClassMethodTest extends TestResultsReporting {

    /**
     * Unit tests for the ClassMethod object.
     */
    @Test
    public void testExpected() {
        verifyNotNull(ClassMethod.addFactory("String"));
    }

    /**
     * Unit tests for the ClassMethod object.
     */
    @Test
    public void testClassMethod() {
        final ClassMethod classMethod = new ClassMethod();
        verifyNotNull(classMethod);
        log.debug("classMethod.debugString() = {}", classMethod.debugString());
        log.debug("classMethod.toCode() =\n{}", classMethod.toCode());
    }

    /**
     * Unit tests for the ClassMethod object.
     */
    @Test
    public void testClassMethodString() {
        final ClassMethod classMethod = new ClassMethod("String", "method");
        verifyNotNull(classMethod);
        log.debug("classMethod.debugString() = {}", classMethod.debugString());
        log.debug("classMethod.toCode() =\n{}", classMethod.toCode());
    }

    /**
     * Unit tests for the ClassMethod object.
     */
    @Test
    public void testClassMethodDate() {
        final ClassMethod classMethod = new ClassMethod("Date", "date");
        verifyNotNull(classMethod);
        log.debug("classMethod.debugString() = {}", classMethod.debugString());
        log.debug("classMethod.toCode() =\n{}", classMethod.toCode());
    }
}
