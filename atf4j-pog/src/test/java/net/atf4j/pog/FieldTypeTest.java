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
 * Unit test for FieldType class.
 */
public class FieldTypeTest {

    /**
     * Unit tests for the FieldType object.
     */
    @Test
    public void testFieldType() {
        verifyEqual(FieldType.OBJECT, FieldType.initialise());
    }

    /**
     * Unit tests for the FieldType object.
     */
    @Test
    public void testFromString() {
        verifyEqual(FieldType.BOOLEAN, FieldType.fromString(FieldType.BOOLEAN.toString()));
        verifyEqual(FieldType.BYTE, FieldType.fromString(FieldType.BYTE.toString()));
        verifyEqual(FieldType.DATE, FieldType.fromString(FieldType.DATE.toString()));
        verifyEqual(FieldType.NUMBER, FieldType.fromString(FieldType.NUMBER.toString()));
        verifyEqual(FieldType.OBJECT, FieldType.fromString(FieldType.OBJECT.toString()));
        verifyEqual(FieldType.STRING, FieldType.fromString(FieldType.STRING.toString()));
        verifyEqual(FieldType.Other, FieldType.fromString(FieldType.Other.toString()));
    }

}
