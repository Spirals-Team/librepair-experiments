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
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import net.atf4j.core.TestResultsReporting;

/**
 * Unit Test for CsvFile class.
 */
public class CsvFileTest extends TestResultsReporting {

    private static final String EXPECTED_HEADER = "HeaderLine [fields=[ColumnOne, ColumnTwo, ColumnThree, ColumnFour]]";
    private static final String MISSING_CSV = "missing.csv";
    private static final String TEST_DATA_CSV = "TestData.csv";

    /**
     * The TestData Class.
     */
    public class TestData extends CsvFile {

        /**
         * Instantiates a new test data.
         */
        public TestData() {
            super();
        }
    }

    /**
     * Test constructor with missing file.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test(expected = FileNotFoundException.class)
    public void testConstructorWithMissingFile() throws FileNotFoundException {
        verifyNotNull(new CsvFile(MISSING_CSV));
        fail("expected FileNotFoundException");
    }

    /**
     * Test read missing file.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test(expected = FileNotFoundException.class)
    public void testReadMissingFile() throws FileNotFoundException {
        verifyNotNull(CsvFile.read(MISSING_CSV));
        fail("expected FileNotFoundException");
    }

    /**
     * Test load missing file.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @Test(expected = FileNotFoundException.class)
    public void testLoadMissingFile() throws FileNotFoundException {
        final CsvFile csvFile = new CsvFile();
        verifyNotNull(csvFile);
        csvFile.load(MISSING_CSV);
        fail("expected FileNotFoundException");
    }

    /**
     * Test method for CsvFile.
     *
     * @throws Exception the exception
     */
    @Test
    public void testExpectedUsage() throws Exception {
        final TestData testData = new TestData();
        log.debug(testData.toString());
        assertNotNull(testData);
    }

    /**
     * Test constructor with data present.
     *
     * @throws Exception the exception
     */
    @Test
    public void testConstructorWithDataPresent() throws Exception {
        final CsvFile csvFile = new CsvFile(TEST_DATA_CSV);
        log.debug(csvFile.debugString());
        verifyNotNull(csvFile);
    }

    /**
     * Test read present data.
     *
     * @throws Exception the exception
     */
    @Test
    public void testReadPresentData() throws Exception {
        final CsvFile csvFile = CsvFile.read(TEST_DATA_CSV);
        log.debug(csvFile.debugString());
        verifyNotNull(csvFile);
        verifyContent(csvFile);
    }

    /**
     * Verify CsvFile object.
     *
     * @param csvFile the csv file
     */
    private void verifyContent(final CsvFile csvFile) {
        verifyNotNull(csvFile);
        log.debug(csvFile.debugString());
        final HeaderLine header = csvFile.getHeaderLine();
        assertEquals(EXPECTED_HEADER, header.debugString());
        log.debug("header = {}", header);
        for (int i = 1; i < csvFile.rowCount(); i++) {
            final CsvRow csvRow = csvFile.getRow(i);
            log.debug("{}", csvRow);
            for (int j = 1; j < csvRow.length(); j++) {
                final String expected = String.format("data-%s-%s", i, j);
                final String actual = csvRow.getField(j);
                assertEquals(expected, actual);
            }
        }
    }

    /**
     * Test method for CsvFile.
     *
     * @throws Exception the exception
     */
    @Test
    public void testScan() throws Exception {
        final CsvFile data = CsvFile.read(TEST_DATA_CSV);
        final Object[] array = data.toArray();
        verifyNotNull(array);
    }
}
