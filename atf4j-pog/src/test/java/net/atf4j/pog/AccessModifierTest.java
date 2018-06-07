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

import static net.atf4j.core.Verify.verifyEqual;

import org.junit.Test;

/**
 * Unit test for AccessModifier class.
 */
public class AccessModifierTest {

    /**
     * Unit tests for the AccessModifier object.
     */
    @Test
    public void testAccessModifier() {
        verifyEqual(AccessModifier.DEFAULT, AccessModifier.initialise());
    }

    /**
     * Unit tests for the AccessModifier object.
     */
    @Test
    public void testFromString() {
        verifyEqual(AccessModifier.DEFAULT, AccessModifier.fromString(AccessModifier.DEFAULT.toString()));
        verifyEqual(AccessModifier.PUBLIC, AccessModifier.fromString(AccessModifier.PUBLIC.toString()));
        verifyEqual(AccessModifier.PROTECTED, AccessModifier.fromString(AccessModifier.PROTECTED.toString()));
        verifyEqual(AccessModifier.PRIVATE, AccessModifier.fromString(AccessModifier.PRIVATE.toString()));
    }

}
