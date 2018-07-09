
package coaching.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Data Access object.
 *
 * Uses domain language to provide Create Read Update Delete interface.
 */
public abstract class AbstractDao implements DaoInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The connection factory. */
    protected ConnectionFactory connectionFactory;
    
    /** The result set. */
    protected ResultSet resultSet;
    
    /** The result set meta data. */
    protected ResultSetMetaData resultSetMetaData;

    /** The driver. */
    private String driver;
    
    /** The url. */
    private String url;
    
    /** The username. */
    private String username;
    
    /** The password. */
    private String password;

    /**
     * The Constructor.
     */
    public AbstractDao() {
        super();
    }

    /**
     * Creates a new instance of AbstractDao.
     *
     * @param driverClassName
     *            the driver class name
     */
    public AbstractDao(final String driverClassName) {
        try {
            driver = driverClassName;
            Class.forName(driverClassName);
        } catch (final ClassNotFoundException e) {
            log.error(e.toString());
        }
    }

    /**
     * Creates a new instance of AbstractDao.
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
    public AbstractDao(final String driverClassName,
            final String connectionUrl,
            final String username,
            final String password) {
        this(driverClassName);
        connectionFactory = new ConnectionFactory(driverClassName, connectionUrl, username, password);
    }

    /*
     * (non-Javadoc)
     * @see coaching.jdbc.DaoInterface#setDriver(java.lang.String)
     */
    @Override
    public DaoInterface setDriver(final String driverClassName) {
        driver = driverClassName;
        try {
            Class.forName(driverClassName);
        } catch (final ClassNotFoundException e) {
            log.error(e.toString());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * @see coaching.jdbc.DaoInterface#setUrl(java.lang.String)
     */
    @Override
    public DaoInterface setUrl(final String url) {
        this.url = url;
        return this;
    }

    /*
     * (non-Javadoc)
     * @see coaching.jdbc.DaoInterface#setUsername(java.lang.String)
     */
    @Override
    public DaoInterface setUsername(final String username) {
        this.username = username;
        return this;
    }

    /*
     * (non-Javadoc)
     * @see coaching.jdbc.DaoInterface#setPassword(java.lang.String)
     */
    @Override
    public DaoInterface setPassword(final String password) {
        this.password = password;
        return this;
    }

    /**
     * Execute a SQL insert statement for CRUD interface.
     *
     * @param sql the sql
     * @return this as fluent interface.
     */
    @Override
    public DaoInterface create(final String sql) {
        return executePreparedStatement(sql);
    }

    /**
     * Execute a SQL select statement for CRUD interface.
     *
     * @param sql the sql
     * @return this as fluent interface.
     */
    @Override
    public DaoInterface read(final String sql) {
        return executePreparedStatement(sql);
    }

    /**
     * Execute a SQL update statement for CRUD interface.
     *
     * @param sql
     *            the sql
     * @return this as fluent interface.
     */
    @Override
    public DaoInterface update(final String sql) {
        return executePreparedStatement(sql);
    }

    /**
     * Execute a SQL delete statement for CRUD interface.
     *
     * @param sql
     *            the sql
     * @return this as fluent interface.
     */
    @Override
    public DaoInterface delete(final String sql) {
        return executePreparedStatement(sql);
    }

    /**
     * execute an sql statement.
     *
     * @param sql
     *            the sql
     * @return the dao interface
     */
    protected DaoInterface executePreparedStatement(final String sql) {
        try {
            final Connection connection = connectionFactory.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            final int result = preparedStatement.executeUpdate();
            log.info("Rows updated: {}", result);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.toString());
        }
        return this;
    }

    /**
     * Execute query.
     *
     * @param sql the sql
     * @return the dao interface
     */
    protected DaoInterface executeQuery(final String sql) {
        try {
            Connection connection = connectionFactory.getConnection();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            processResultSet(resultSet);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            log.error(e.toString());
        }
        return this;
    }

    /**
     * Handle result set.
     *
     * @param resultSet
     *            the result set
     * @throws SQLException
     *             the SQL exception
     */
    private void processResultSet(final ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            log.info(processRow(resultSet));
        }
    }

    /**
     * Process row.
     *
     * @param resultSet
     *            the result set
     * @return the string
     * @throws SQLException
     *             the SQL exception
     */
    private String processRow(final ResultSet resultSet) throws SQLException {
        final ResultSetMetaData metaData = resultSet.getMetaData();
        final int colCount = metaData.getColumnCount();
        final StringBuffer output = new StringBuffer();
        for (int i = 1; i <= colCount; i++) {
            final String columnName = metaData.getColumnName(i);
            final Object value = resultSet.getObject(i);
            if (value == null) {
                output.append(String.format("%s = null,", columnName));
            } else {
                output.append(String.format("%s = %s,", columnName, value.toString().trim()));
            }
        }
        return output.toString();
    }
}
