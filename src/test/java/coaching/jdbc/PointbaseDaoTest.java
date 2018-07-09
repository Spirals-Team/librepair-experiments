
package coaching.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for PointbaseDao class.
 */
@Ignore("Requires PointBase DB availability")
public class PointbaseDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(PointbaseDaoTest.class);

    private static final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";
    private final static String JDBC_URL = "jdbc:pointbase://localhost:9092/sample";
    private final static String USERNAME = "PBPUBLIC";
    private final static String PASSWORD = "PBPUBLIC";

    /**
     * Unit Test to point base customer dao.
     */
    @Test
    public void testPointBaseCustomerDao() {
        final DaoInterface dao = new PointbaseDao();
        assertNotNull(dao);
    }

    /**
     * Unit Test to point base customer dao url user password.
     */
    @Test
    public void testPointBaseCustomerDaoTypical() {
        final PointbaseDao dao = new PointbaseDao();
        assertNotNull(dao);
        dao.create(DaoInterface.INSERT_SQL);
        dao.read(DaoInterface.SELECT_SQL);
        dao.update(DaoInterface.UPDATE_SQL);
        dao.delete(DaoInterface.DELETE_SQL);
    }
}
