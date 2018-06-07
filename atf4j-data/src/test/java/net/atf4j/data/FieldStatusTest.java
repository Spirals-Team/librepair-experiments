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

package net.atf4j.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for FieldStatus objects.
 */
public final class FieldStatusTest extends TestResultsReporting {

    /**
     * Unit tests for the FieldStatus object.
     */
    @Test
    public void testInitialise() {
        final FieldStatus fieldStatus = FieldStatus.initialise();
        verifyNotNull(fieldStatus);
        assertEquals(FieldStatus.PRISTINE, fieldStatus);
    }

    /**
     * Unit tests for the FieldStatus object.
     */
    @Test
    public void testValueOf() {
        assertEquals(FieldStatus.PRISTINE, FieldStatus.fromString(FieldStatus.PRISTINE.toString()));
        assertEquals(FieldStatus.CHANGED, FieldStatus.fromString(FieldStatus.CHANGED.toString()));
        assertEquals(FieldStatus.PERSISTED, FieldStatus.fromString(FieldStatus.PERSISTED.toString()));
        assertEquals(FieldStatus.UNWANTED, FieldStatus.fromString(FieldStatus.UNWANTED.toString()));
    }

    /**
     * Test value of Pristine.
     */
    @Test
    public void testValueOfPristine() {
        final FieldStatus expected = FieldStatus.PRISTINE;
        final String string = expected.toString();
        final FieldStatus actual = FieldStatus.fromString(string);
        assertEquals(expected, actual);
    }

    /**
     * Unit tests for the FieldStatus object.
     */
    @Test
    public void testValueOfChanged() {
        assertEquals(FieldStatus.CHANGED, FieldStatus.fromString(FieldStatus.CHANGED.toString()));
    }

    /**
     * Unit tests for the FieldStatus object.
     */
    @Test
    public void testValueOfPersisted() {
        assertEquals(FieldStatus.PERSISTED, FieldStatus.fromString(FieldStatus.PERSISTED.toString()));
    }

    /**
     * Unit tests for the FieldStatus object.
     */
    @Test
    public void testValueOfUnwanted() {
        assertEquals(FieldStatus.UNWANTED, FieldStatus.fromString(FieldStatus.UNWANTED.toString()));
    }

}
