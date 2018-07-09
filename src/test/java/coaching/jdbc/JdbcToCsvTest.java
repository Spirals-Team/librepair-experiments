
package coaching.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test the CsvToJdbc class.
 */
public class JdbcToCsvTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(JdbcToCsvTest.class);

    @Test
    @Ignore("Requires Database")
    public void testJdbcToCsv() throws Exception {
        final JdbcToCsv instance = new JdbcToCsv();
        assertNotNull(instance);
        LOG.info(instance.toString());
    }

}
