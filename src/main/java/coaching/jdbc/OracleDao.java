
package coaching.jdbc;

/**
 * Data Access object for Oracle sample customer table.
 */
public final class OracleDao extends AbstractDataAccessObject {

    /** The Constant JDBC_DRIVER. */
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /** The Constant JDBC_URL. */
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:sample";

    /** The Constant USER. */
    private static final String USER = "user";

    /** The Constant PASSWORD. */
    private static final String PASSWORD = "password";

    /** The Constant SELECT_SQL. */
    private static final String SELECT_SQL = "SELECT * FROM table";

    /** The Constant INSERT_SQL. */
    private static final String INSERT_SQL = "INSERT INTO table (field-name...) VALUES (values...)";

    /** The Constant UPDATE_SQL. */
    private static final String UPDATE_SQL = "UPDATE table SET field-name = field-value WHERE key=value";

    /** The Constant DELETE_SQL. */
    private static final String DELETE_SQL = "DELETE FROM table WHERE key=value";

    /**
     * Instantiates a new oracle dao.
     */
    public OracleDao() {
        super(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
    }

    /**
     * Creates the.
     */
    public void create() {
        super.create(INSERT_SQL);
    }

    /**
     * Read.
     */
    public void read() {
        super.read(SELECT_SQL);
    }

    /**
     * Update.
     */
    public void update() {
        super.update(UPDATE_SQL);
    }

    /**
     * Delete.
     */
    public void delete() {
        super.delete(DELETE_SQL);
    }

}
