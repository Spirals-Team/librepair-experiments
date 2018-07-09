
package coaching.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for the ConnectionFactory class.
 */
public class ConnectionFactoryTest {

    @Test
    @Ignore("Requires SQLite3")
    public void testSqLiteConnectionFactory() throws SQLException {
        final String JDBC_DRIVER = "org.sqlite.JDBC";
        final String JDBC_URL = "jdbc:sqlite::memory";
        final String USERNAME = "";
        final String PASSWORD = "";
        final ConnectionFactory connectionFactory = new ConnectionFactory(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
        assertNotNull(connectionFactory);
        final Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        connection.close();
    }

    @Test
    @Ignore("Requires Pointbase")
    public void testPointbaseConnectionFactory() throws SQLException {
        final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";
        final String JDBC_URL = "jdbc:pointbase:server://localhost/sample";
        final String USERNAME = "PBPUBLIC";
        final String PASSWORD = "PBPUBLIC";

        final ConnectionFactory connectionFactory = new ConnectionFactory(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
        assertNotNull(connectionFactory);
        final Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        connection.close();
    }

    @Test
    @Ignore("Requires MySQL")
    public void testMySqlConnectionFactory() throws SQLException {
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String JDBC_URL = "jdbc:mysql://localhost:3306/student";
        final String USERNAME = "root";
        final String PASSWORD = "root";

        final ConnectionFactory connectionFactory = new ConnectionFactory(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
        assertNotNull(connectionFactory);
        final Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        connection.close();
    }

    @Test
    @Ignore("Requires Oracle")
    public void testConnectionFactory() throws SQLException {
        final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
        final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:sample";
        final String USERNAME = "user";
        final String PASSWORD = "password";

        final ConnectionFactory connectionFactory = new ConnectionFactory(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
        assertNotNull(connectionFactory);
        final Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        connection.close();
    }

    @Test
    @Ignore("Requires PostgreSQL")
    public void testPostgresqlConnectionFactory() throws SQLException {
        final String JDBC_DRIVER = "org.postgresql.Driver";
        final String JDBC_URL = "jdbc:postgresql://localhost";
        final String USERNAME = "postgres";
        final String PASSWORD = "password";

        final ConnectionFactory connectionFactory = new ConnectionFactory(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
        assertNotNull(connectionFactory);
        final Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        connection.close();
    }

}
