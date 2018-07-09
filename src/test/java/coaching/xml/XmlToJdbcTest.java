
package coaching.xml;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import coaching.jdbc.MySqlDao;

/**
 * Unit test for the XmlToJdbc class.
 */
public class XmlToJdbcTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(XmlToJdbcTest.class);

    /** Default JDBC DRIVER . */
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    /** Default JDBC URL. */
    private static final String JDBC_URL = "jdbc:postgresql://localhost";

    /** Default USERNAME for connection. */
    private static final String USERNAME = "postgres";

    /** Default PASSWORD for connection. */
    private static final String PASSWORD = "password";

    /** Default data source filename. */
    private final String filename = "./data/data.csv";

    /** Default table name. */
    private final String tableName = "tableName";

    /**
     * Unit Test for xml to jdbc.
     */
    @Test
    public void testXmlToJdbc() {
        final MySqlDao instance = new XmlToJdbc();
        assertNotNull(instance);

        assertEquals(instance, instance.setDriver(JDBC_DRIVER));
        assertEquals(instance, instance.setUrl(JDBC_URL));
        assertEquals(instance, instance.setUsername(USERNAME));
        assertEquals(instance, instance.setPassword(PASSWORD));
    }

}
