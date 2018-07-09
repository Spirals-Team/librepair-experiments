
package coaching.csv;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CsvToJdbcTest class.
 */
public class CsvToXmlTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CsvToXmlTest.class);

    /** The filename. */
    private final String filename = "./data/data.csv";

    /**
     * Fluent Interface.
     *
     */
    @Test
    public void testFluentInterface() {
        final CsvToXml instance = new CsvToXml();
    }

}
