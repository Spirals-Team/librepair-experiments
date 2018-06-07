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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assume;
import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A Unit test class for Datum.
 */
public final class DatumTest extends TestResultsReporting {

    /**
     * The MockDatum Class.
     */
    class MockDatum extends AbstractDatum {
    }

    /**
     * Test method for Datum.
     */
    @Test
    public void testDefaultConstructor() {
        final MockDatum mockDatum = new MockDatum();
        assertTrue(mockDatum.isFresh());
        assertFalse(mockDatum.isUsed());
        assertFalse(mockDatum.isChanged());
        assertFalse(mockDatum.isDeleted());
    }

    /**
     * Test method for { net.atf4j.data.Datum#setDeleted(boolean)}.
     */
    @Test
    public void testIsDeleted() {
        final MockDatum mockDatum = new MockDatum();
        Assume.assumeFalse(mockDatum.isDeleted());
        mockDatum.deleted(true);
        assertTrue(mockDatum.isDeleted());
    }

    /**
     * Test method for { net.atf4j.data.Datum#isDirty()}.
     */
    @Test
    public void testIsDirty() {
        final MockDatum mockDatum = new MockDatum();
        Assume.assumeFalse(mockDatum.isChanged());
        mockDatum.changed(true);
        assertTrue(mockDatum.isChanged());
    }

    /**
     * Test method for { net.atf4j.data.Datum#isNew()}.
     */
    @Test
    public final void testIsNew() {
        final MockDatum mockDatum = new MockDatum();
        Assume.assumeTrue(mockDatum.isFresh());
        mockDatum.fresh(false);
        assertFalse(mockDatum.isFresh());
    }

    /**
     * Test method for { net.atf4j.data.Datum#setUsed(boolean)}.
     */
    @Test
    public void testIsUsed() {
        final MockDatum mockDatum = new MockDatum();
        Assume.assumeFalse(mockDatum.isUsed());
        mockDatum.used(true);
        assertTrue(mockDatum.isUsed());
    }

    /**
     * Test method for Datum.
     */
    @Test
    public void testToString() {
        assertEquals("MockDatum [isNew=true, isDirty=false, isDeleted=false, isUsed=false]",
                new MockDatum().toString());
    }

}
