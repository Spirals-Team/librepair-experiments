
package coaching.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * A factory for creating Connection objects.
 */
public class ConnectionFactory {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** JDBC_DRIVER to be used. */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /** JDBC_URL to be used. */
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student";

    /** USER to be used. */
    private static final String USER = "root";

    /** PASSWORD to be used. */
    private static final String PASSWORD = "root";

    /** The connection url. */
    private String connectionUrl = JDBC_URL;

    /** The username. */
    private String username = USER;

    /** The password. */
    private String password = PASSWORD;

    /**
     * Instantiates a new connection factory.
     */
    public ConnectionFactory() {
        this(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
    }

    /**
     * Instantiates a new connection factory.
     *
     * @param driverClassName
     *            the driver class name
     * @param connectionUrl
     *            the connection url
     * @param username
     *            the username
     * @param password
     *            the password
     */
    public ConnectionFactory(final String driverClassName,
            final String connectionUrl,
            final String username,
            final String password) {
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        try {
            Class.forName(driverClassName);
        } catch (final ClassNotFoundException e) {
            this.log.error("{}", e.toString());
        }
    }

    /**
     * Get jdbc connection for the database.
     *
     * @return the connection
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.connectionUrl, this.username, this.password);
        } catch (final Exception e) {
            this.log.error("{}", e.toString());
        }
        return null;
    }

}
