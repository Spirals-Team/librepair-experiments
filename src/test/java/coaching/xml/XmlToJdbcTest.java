
package coaching.xml;

import coaching.jdbc.MySqlDao;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for the XmlToJdbc class.
 */
public class XmlToJdbcTest {

    /**
     * Unit Test for xml to jdbc.
     */
    @Test
    public void testXmlToJdbc() {
        final MySqlDao xmlToJdbc = new XmlToJdbc();
        assertNotNull("Value cannot be null", xmlToJdbc);
    }

}
