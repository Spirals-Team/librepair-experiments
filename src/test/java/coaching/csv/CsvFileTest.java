
package coaching.csv;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test CSVFile.
 */
public class CsvFileTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CsvFileTest.class);

    /**
     * Unit Test to csv file.
     */
    public void testCsvFile() {
        LOG.info("testCsvFile()");
        final CsvFile csvFile = new CsvFile();
        assertNotNull(csvFile);
        assertEquals(4, csvFile.rowCount());
        LOG.info(csvFile.toString());
        csvFile.logPretty();
    }

    /**
     * Unit Test to CsvFile class.
     */
    @Test
    public void testCsvFileString() {
        LOG.info("testCsvFileString()");
        final CsvFile csvFile = new CsvFile("/data.csv");
        assertNotNull(csvFile);
        assertEquals(4, csvFile.rowCount());
        LOG.info(csvFile.toString());
        csvFile.logPretty();
    }

    /**
     * Unit Test to CsvFile class.
     */
    @Test
    public void testCsvFileHeader() {
        LOG.info("testCsvFileHeader()");
        final CsvFile csvFile = new CsvFile();
        assertNotNull(csvFile);
        LOG.info(csvFile.toString());
        final String header = csvFile.getHeader();
        assertNotNull(header);
        LOG.info(header.toString());
    }

    /**
     * Unit Test to CsvFile class.
     */
    @Test
    public void testCsvFileRecords() {
        LOG.info("testCsvFileRecords()");
        final CsvFile csvFile = new CsvFile();
        assertNotNull(csvFile);
        for (int index = 0; index < csvFile.rowCount(); index++) {
            final CsvRecord record = csvFile.getRecord(index);
            assertNotNull(record);
            LOG.info(record.toString());
        }
    }
}
