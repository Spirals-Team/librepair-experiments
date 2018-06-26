
package coaching.jdbc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * The Class OracleDaoTest.
 */
@Ignore("Requires Oracle DB availability")
public class OracleDaoTest {

    /**
     * Test oracle dao.
     */
    @Test
    public void testOracleDao() {
        DaoInterface dao = new OracleDao();
        assertNotNull(dao);
    }

    /**
     * Test create.
     */
    @Test
    public void testCreate() {
        final OracleDao oracleDao = new OracleDao();
        assertNotNull(oracleDao);
        oracleDao.create();
    }

    /**
     * Test read.
     */
    @Test
    public void testRead() {
        final OracleDao oracleDao = new OracleDao();
        assertNotNull(oracleDao);
        oracleDao.read();
    }

    /**
     * Test update.
     */
    @Test
    public void testUpdate() {
        final OracleDao oracleDao = new OracleDao();
        assertNotNull(oracleDao);
        oracleDao.update();
    }

    /**
     * Test delete.
     */
    @Test
    public void testDelete() {
        final OracleDao oracleDao = new OracleDao();
        assertNotNull(oracleDao);
        oracleDao.delete();
    }

}
