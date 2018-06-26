
package coaching.jdbc;

import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for the ConnectionFactory class.
 */
public class ConnectionFactoryTest {

    /**
     * Unit Test for typical usage of connection factory.
     *
     * @throws SQLException
     *             the SQL exception
     */
    @Test
    @Ignore("No local DB setup")
    public void testConnectionFactory() throws SQLException {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        assertNotNull(connectionFactory);
        final Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        connection.close();
    }

}
