
package coaching.jdbc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for MySqlDao.
 */
@Ignore("Requires MySQL DB availability")
public class MySqlDaoTest {

    /**
     * Test my sql dao.
     */
    @Test
    public void testMySqlDao() {
        final MySqlDao mySqlDao = new MySqlDao();
        assertNotNull(mySqlDao);
    }

    /**
     * Test create.
     */
    @Test
    public void testCreate() {
        final MySqlDao mySqlDao = new MySqlDao();
        assertNotNull(mySqlDao);
        mySqlDao.create(DaoInterface.INSERT_SQL);
    }

    /**
     * Test read.
     */
    @Test
    public void testRead() {
        final MySqlDao mySqlDao = new MySqlDao();
        assertNotNull(mySqlDao);
        mySqlDao.read(DaoInterface.SELECT_SQL);
    }

    /**
     * Test update.
     */
    @Test
    public void testUpdate() {
        final MySqlDao mySqlDao = new MySqlDao();
        assertNotNull(mySqlDao);
        mySqlDao.update(DaoInterface.UPDATE_SQL);
    }

    /**
     * Test delete.
     */
    @Test
    public void testDelete() {
        final MySqlDao mySqlDao = new MySqlDao();
        assertNotNull(mySqlDao);
        mySqlDao.delete(DaoInterface.DELETE_SQL);
    }

}
