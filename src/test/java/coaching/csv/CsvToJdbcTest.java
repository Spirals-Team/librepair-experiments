
package coaching.csv;

import coaching.jdbc.XmlDAOTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * CsvToJdbcTest Class.
 */
public class CsvToJdbcTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(XmlDAOTest.class);

    /** JDBC DRIVER . */
    private static final String DRIVER = "org.postgresql.Driver";

    /** JDBC URL. */
    private static final String URL = "jdbc:postgresql://localhost";

    /** USERNAME for connection. */
    private static final String USERNAME = "postgres";

    /** PASSWORD for connection. */
    private static final String PASSWORD = "password";

    /** The filename. */
    private final String filename = "./data/data.csv";

    /** The table name. */
    private final String tableName = "tableName";

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(CsvToJdbcTest.class);

    /**
     * Fluent Interface.
     *
     */
    @Test
    public void testFluentInterface() {
        final CsvToJdbc instance = new CsvToJdbc();
        assertEquals(instance, instance.setDriver(DRIVER));
        assertEquals(instance, instance.setUrl(URL));
        assertEquals(instance, instance.setUsername(USERNAME));
        assertEquals(instance, instance.setPassword(PASSWORD));
        assertEquals(instance, instance.setTableName(this.tableName));
        assertEquals(instance, instance.setFilename(this.filename));
    }

}
