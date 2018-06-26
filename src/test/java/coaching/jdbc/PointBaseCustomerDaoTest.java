
package coaching.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * class PointBaseCustomerDaoTest.
 */
@Ignore("Requires PointBase DB availability")
public class PointBaseCustomerDaoTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(PointBaseCustomerDaoTest.class);
    
    /** The Constant JDBC_DRIVER. */
    private static final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";
    
    /** The Constant JDBC_URL. */
    private final static String JDBC_URL = "jdbc:pointbase://localhost:9092/sample";
    
    /** The Constant USER. */
    private final static String USER = "PBPUBLIC";
    
    /** The Constant PASSWORD. */
    private final static String PASSWORD = "PBPUBLIC";

    /**
     * Unit Test to point base customer dao.
     */
    @Test
    public void testPointBaseCustomerDao() {
        final DaoInterface dao = new PointBaseCustomerDao();
        assertNotNull("Value cannot be null", dao);
    }

    /**
     * Unit Test to point base customer dao url user password.
     */
    @Test
    public void testPointBaseCustomerDaoUrlUserPassword() {
        assertNotNull("Value cannot be null", new PointBaseCustomerDao());
        try {
            final DaoInterface dao = new PointBaseCustomerDao();
            assertNotNull("Value cannot be null", dao);
        } catch (final Exception exception) {
            LOG.error("{}", exception.toString());
        }
    }

    /**
     * Unit Test to point base customer dao url user password.
     */
    @Test
    public void testPointBaseCustomerDaoTypical() {
        final PointBaseCustomerDao dao = new PointBaseCustomerDao();
        assertNotNull("Value cannot be null", dao);
        try {
            dao.create();
            dao.read();
            dao.update();
            dao.delete();
        } catch (final Exception exception) {
            LOG.error("{}", exception.toString());
        }
    }
}
