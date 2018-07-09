
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
public class XmlDaoTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(XmlDaoTest.class);

    /**
     * Unit Test to xml DAO.
     */
    @Test
    public void testXmlDao() {
        final XmlDao dao = new XmlDao();
        assertNotNull(dao);
        LOG.info(dao.toString());
    }

    /**
     * Unit Test to xml dao url user password.
     */
    @Test
    public void testXmlDaoUrlUserPassword() {
        final XmlDao dao = new XmlDao();
        assertNotNull(dao);
        LOG.info(dao.toString());
    }

}
