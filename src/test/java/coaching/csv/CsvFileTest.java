
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
    private static final Logger log = LoggerFactory.getLogger(CsvFileTest.class);

    /**
     * Unit Test to csv file.
     */
    public void testCsvFile() {
        log.info("testCsvFile()");
        final CsvFile csvFile = new CsvFile();
        assertNotNull("Value cannot be null", csvFile);
        assertEquals(4, csvFile.size());
        log.info("{}", csvFile);
        csvFile.logPretty();
    }

    /**
     * Unit Test to CsvFile class.
     */
    @Test
    public void testCsvFileString() {
        log.info("testCsvFileString()");
        final CsvFile csvFile = new CsvFile("/data.csv");
        assertNotNull("Value cannot be null", csvFile);
        assertEquals(4, csvFile.size());
        log.info("{}", csvFile);
        csvFile.logPretty();
    }

    /**
     * Unit Test to CsvFile class.
     */
    @Test
    public void testCsvFileHeader() {
        log.info("testCsvFileHeader()");
        final CsvFile csvFile = new CsvFile();
        assertNotNull("Value cannot be null", csvFile);
        log.info("{}", csvFile);
        final String header = csvFile.getHeader();
        assertNotNull("Value cannot be null", header);
        log.info("{}", header);
    }

    /**
     * Unit Test to CsvFile class.
     */
    @Test
    public void testCsvFileRecords() {
        log.info("testCsvFileRecords()");
        final CsvFile csvFile = new CsvFile();
        assertNotNull("Value cannot be null", csvFile);
        for (int index = 0; index < csvFile.size(); index++) {
            final CsvRecord record = csvFile.getRecord(index);
            assertNotNull("Value cannot be null", record);
            log.info("{}", record);
        }
    }
}
