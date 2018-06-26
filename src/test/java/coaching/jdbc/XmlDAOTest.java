
package coaching.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for XmlDAO class.
 */
@Ignore("Requires PointBase DB availability")
public class XmlDAOTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(XmlDAOTest.class);
    
    /** The Constant JDBC_DRIVER. */
    private static final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";
    
    /** The Constant JDBC_URL. */
    private static final String JDBC_URL = "jdbc:pointbase:server://localhost/sample";
    
    /** The Constant USER. */
    private static final String USER = "PBPUBLIC";
    
    /** The Constant PASSWORD. */
    private static final String PASSWORD = "PBPUBLIC";
    
    /** The Constant SQL. */
    private static final String SQL = "SELECT * from customers";

    /**
     * Unit Test to xml DAO.
     */
    @Test
    public void testXmlDao() {
        final XmlDao dao = new XmlDao();
        assertNotNull("Value cannot be null", dao);
        LOG.info("{}", dao);
    }

    /**
     * Unit Test to xml dao url user password.
     */
    @Test
    public void testXmlDaoUrlUserPassword() {
        final XmlDao dao = new XmlDao();
        assertNotNull("Value cannot be null", dao);
        LOG.info("{}", dao);
    }

}
