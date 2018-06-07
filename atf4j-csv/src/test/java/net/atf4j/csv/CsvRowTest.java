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

package net.atf4j.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * A UnitTest for CsvRow objects.
 */
public class CsvRowTest extends TestResultsReporting {

    private static final String TEST_ROW = "One, Two, Three, Four";
    private static final String EXPECTED = "CsvRow [fields=[One, Two, Three, Four]]";

    /**
     * test CsvRow object.
     */
    @Test
    public void testDefaultCsvRow() {
        final CsvRow csvRow = new CsvRow();
        assertNotNull(csvRow);
    }

    /**
     * test CsvRow object.
     */
    @Test
    public void testEmptyCsvRow() {
        final CsvRow csvRow = new CsvRow("");
        assertNotNull(csvRow);
    }

    /**
     * test CsvRow object.
     */
    @Test
    public void testGetField() {
        final CsvRow csvRow = new CsvRow(TEST_ROW);
        assertNotNull(csvRow);
        assertEquals(4, csvRow.length());
        assertEquals("One", csvRow.getField(1));
        assertEquals("Two", csvRow.getField(2));
        assertEquals("Three", csvRow.getField(3));
        assertEquals("Four", csvRow.getField(4));
    }

    /**
     * test CsvRow object.
     */
    @Test
    public void testGetFields() {
        final CsvRow csvRow = new CsvRow(TEST_ROW);
        assertNotNull(csvRow);
        final String[] fields = csvRow.getFields();
        assertNotNull(fields);
        assertEquals(4, fields.length);
        for (final String field : fields) {
            log.debug("field = {}", field);
        }
    }

    /**
     * test CsvRow object.
     */
    @Test
    public void testToString() {
        final CsvRow csvRow = new CsvRow(TEST_ROW);
        log.debug("csvRow = {}", csvRow);
        assertNotNull(csvRow);
        final String actual = csvRow.toString();
        log.debug("csvRow.toString() = {}", actual);
        assertNotNull(actual);
        assertEquals(TEST_ROW, actual);
    }

    /**
     * Test method for CsvRow.
     */
    @Test
    public void testDebugString() {
        final CsvRow csvRow = new CsvRow(TEST_ROW);
        log.debug("csvRow = {}", csvRow);
        assertNotNull(csvRow);
        final String actual = csvRow.debugString();
        log.debug("csvRow.debugString() = {}", actual);
        assertNotNull(actual);
        assertEquals(EXPECTED, actual);
    }

}
