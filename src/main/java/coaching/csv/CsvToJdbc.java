
package coaching.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CsvToJdbc Class.
 */
public class CsvToJdbc {

    /** provides logging. */
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The csv file. */
    private CsvFile csvFile;

    /** The filename. */
    private String filename;

    /** The driver. */
    private String driver;

    /** The url. */
    private String url;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The table name. */
    private String tableName;

    /**
     * Instantiates a new csv to jdbc.
     */
    public CsvToJdbc() {
        super();
    }

    /**
     * Instantiates a new csv to jdbc.
     *
     * @param csvFile
     *            the csv file
     */
    public CsvToJdbc(final CsvFile csvFile) {
        this.csvFile = csvFile;
    }

    /**
     * Column headers.
     *
     * @return the column headers
     */
    public String getColumnHeaders() {
        return this.csvFile.getColumnNames();
    }

    /**
     * Make jdbc connection.
     *
     * @param driver
     *            the driver
     * @param url
     *            the url
     * @param user
     *            the user
     * @param password
     *            the password
     * @return the connection
     */
    private Connection makeJdbcConnection(final String driver,
            final String url,
            final String user,
            final String password) {
        try {
            Class.forName(driver);
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (final SQLException e) {
                this.log.error("{}", e);
            }
        } catch (final ClassNotFoundException e) {
            this.log.error("{}", e);
        }
        return null;
    }

    /**
     * Make statement.
     *
     * @param connection
     *            the connection
     * @return the statement
     * @throws SQLException
     *             the SQL exception
     */
    private Statement makeStatement(final Connection connection) throws SQLException {
        return connection.createStatement();
    }

    /**
     * Process.
     */
    public void process() {
        process(this.driver, this.url, this.username, this.password, "tableName");
    }

    /**
     * Process.
     *
     * @param driver
     *            the driver
     * @param url
     *            the url
     * @param user
     *            the user
     * @param password
     *            the password
     * @param table
     *            the table
     */
    protected void process(final String driver,
            final String url,
            final String user,
            final String password,
            final String table) {
        makeJdbcConnection(driver, url, user, password);
        this.csvFile = new CsvFile(this.filename);
        for (int index = 0; index < this.csvFile.size(); index++) {
            final CsvRecord record = this.csvFile.getRecord(index);
            this.log.info(record.toString());
            writeRecord(record);
        }
    }

    /**
     * csv file.
     *
     * @param csvFile
     *            the csv file
     * @return the csv to jdbc
     */
    public CsvToJdbc setCsvFile(final CsvFile csvFile) {
        this.csvFile = csvFile;
        return this;
    }

    /**
     * driver.
     *
     * @param driver
     *            the driver
     * @return the csv to jdbc
     */
    public CsvToJdbc setDriver(final String driver) {
        this.driver = driver;
        return this;
    }

    /**
     * filename.
     *
     * @param filename
     *            the filename
     * @return the csv to jdbc
     */
    public CsvToJdbc setFilename(final String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * password.
     *
     * @param password
     *            the password
     * @return the csv to jdbc
     */
    public CsvToJdbc setPassword(final String password) {
        this.password = password;
        return this;
    }

    /**
     * table name.
     *
     * @param tableName
     *            the table name
     * @return the csv to jdbc
     */
    public CsvToJdbc setTableName(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * url.
     *
     * @param url
     *            the url
     * @return the csv to jdbc
     */
    public CsvToJdbc setUrl(final String url) {
        this.url = url;
        return this;
    }

    /**
     * username.
     *
     * @param username
     *            the username
     * @return the csv to jdbc
     */
    public CsvToJdbc setUsername(final String username) {
        this.username = username;
        return this;
    }

    /**
     * Write.
     *
     * @param record the record
     */
    public void writeRecord(final CsvRecord record) {
        final StringBuffer sql = createSql(record);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
            statement = makeStatement(connection);
            if (statement.execute(sql.toString())) {
                this.log.info("ok {}", statement.getResultSet().toString());
            } else {
                if (statement.getUpdateCount() == 1) {
                    this.log.info("ok {}", statement.getResultSet().toString());
                } else {
                    this.log.info("failed {}", statement.getWarnings());
                }
            }
            statement.close();
            connection.close();
        } catch (final SQLException e) {
            this.log.error("{}", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (final SQLException e) {
                this.log.error("{}", e);
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (final SQLException e) {
                this.log.error("{}", e);
            }
        }
    }

    /**
     * Creates the sql.
     *
     * insert into %table% (%field%,...) from (%value%,...)
     *
     * @param record
     *            the record
     * @return the string buffer
     */
    protected StringBuffer createSql(final CsvRecord record) {
        final StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(this.tableName);
        sql.append(getColumnHeaders());
        sql.append(" VALUES ");
        sql.append(record.toString());
        this.log.info(sql.toString());
        return sql;
    }
}
