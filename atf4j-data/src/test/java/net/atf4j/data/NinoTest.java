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

import static net.atf4j.core.Verify.verifyEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for National Insurance number objects.
 */
public final class NinoTest extends TestResultsReporting {

    /** Blank National Insurance No. */
    private static final String BLANK = "";

    /** Invalid National Insurance No. */
    private static final String INVALID = "XX-99-99-99X";

    /** Valid National Insurance No. */
    private static final String VALID = "AA-99-99-99A";

    /**
     * test NationalInsurance object.
     */
    @Test
    public void testNationalInsurance() {
        verifyNotNull(new Nino());
    }

    /**
     * Test national insurance pic.
     */
    @Ignore("Fails")
    @Test
    public void testInvalidNino() {
        final Nino nino = new Nino(INVALID);
        verifyNotNull(nino);
        verifyEqual(INVALID, nino);
    }

    /**
     * Test valid nino.
     */
    @Test
    public void testValidNino() {
        final Nino nino = new Nino(VALID);
        this.log.debug("{}", nino);
        verifyNotNull(nino);
        assertEquals(VALID, nino.toString());
    }

    /**
     * Test method for void.
     */
    @Test
    public void testCreate() {
        final Nino nino = Nino.create();
        verifyNotNull(nino);
    }

    /**
     * test NationalInsurance object.
     */
    @Test
    public void testVerify() {
        assertFalse(Nino.verify(BLANK));
        assertFalse(Nino.verify(INVALID));
        assertTrue(Nino.verify(VALID));
    }

    /**
     * Test set get nino.
     */
    @Test
    public void testSetGetNino() {
        final Nino nino = new Nino();
        verifyNotNull(nino);
        assertEquals(VALID, nino.set(VALID).get());
        // assertEquals(INVALID, nino.set(INVALID).get());
    }

    /**
     * Test method for NationalInsurance.
     */
    @Test
    public void testToString() {
        final String string = new Nino().toString();
        verifyNotNull(string);
    }
}
