/**
 * JdbcTest.java
 *
 * Created on 06 June 2005, 09:28
 */

package coaching.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract JDBC base class to be extended to create a DAO.
 */
public abstract class JdbcBase {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The JDBC connection. */
    protected Connection connection = null;

    /** The SQL statement. */
    protected Statement statement = null;

    /** The results of the query. */
    protected ResultSet resultSet = null;

    /** The metadata for the results. */
    protected ResultSetMetaData resultSetMetaData = null;

    /** The metadata for the entire database. */
    protected DatabaseMetaData databaseMetaData = null;

    /**
     * Instantiates a new jdbc base.
     */
    public JdbcBase() {
        super();
        try {
            Class.forName(JdbcConfig.driver());

            try {
                connection = DriverManager
                    .getConnection(
                            JdbcConfig.url(),
                            JdbcConfig.username(),
                            JdbcConfig.password());
            } catch (final SQLException e) {
                log.error(e.toString());
            }

        } catch (final ClassNotFoundException e) {
            log.error(e.toString());
        }
    }

    /**
     * Query.
     *
     * @return the jdbc base
     * @throws SQLException the SQL exception
     */
    protected JdbcBase query() throws SQLException {
        final String query = JdbcConfig.query();
        return query(query);
    }

    /**
     * Query.
     *
     * @param query the query
     * @return the jdbc base
     * @throws SQLException the SQL exception
     */
    protected JdbcBase query(final String query) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        resultSetMetaData = resultSet.getMetaData();
        return this;
    }

    /**
     * Close.
     */
    public void close() {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (final SQLException e) {
            log.error(e.toString());
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    public void finalize() {
        close();
    }

}
