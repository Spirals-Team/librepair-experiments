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
 * A UnitTest for PojoGenerator objects.
 */
public final class PojoGeneratorTest extends TestResultsReporting {

    /**
     * Test method for void.
     *
     * @throws VerificationError the atf4j exception
     */
    @Test
    public void testExpectedUsage() throws VerificationError {
        final FluentPojoGenerator pojoGenerator = new FluentPojoGenerator();
        verifyNotNull(pojoGenerator);
        pojoGenerator.setClassName("FluentPojo");
        pojoGenerator.addStringField("string");
        pojoGenerator.addDateField("date");
        pojoGenerator.addBooleanField("bool");
        pojoGenerator.generate();
    }

}
