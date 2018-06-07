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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * The ChordTest Class.
 */
public final class ChordTest extends TestResultsReporting {

    /**
     * Test set stem.
     */
    @Test
    public void testSetGetStem() {
        final Chord chord = new Chord();
        this.log.debug("new Chord() = {}", chord);
        assertNotNull(chord);
        final String stem = "Stem";
        chord.setStem(stem);
        assertEquals(stem, chord.getStem());
    }

    /**
     * Test add.
     */
    @Test
    public void testAdd() {
        final Chord chord = new Chord();
        this.log.debug("new Chord() = {}", chord);
        assertNotNull(chord);
        chord.add(new Chord());
    }

    /**
     * Test debug string.
     */
    @Test
    public void testDebugString() {
        final Chord chord = new Chord();
        this.log.debug("new Chord() = {}", chord.debugString());
        assertNotNull(chord);
        chord.add(new Chord());
        this.log.debug("new Chord() = {}", chord.debugString());
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString() {
        final Chord chord = new Chord();
        this.log.debug("new Chord() = {}", chord.toString());
        assertNotNull(chord);
        chord.add(new Chord());
        this.log.debug("new Chord() = {}", chord.toString());
    }

}
