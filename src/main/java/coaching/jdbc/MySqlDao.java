
package coaching.jdbc;

/**
 * MySqlDao class.
 */
public class MySqlDao extends AbstractDataAccessObject {

    /** The Constant JDBC_DRIVER. */
    private static final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";

    /** The Constant JDBC_URL. */
    private static final String JDBC_URL = "jdbc:pointbase://localhost:9092/sample";

    /** The Constant USERNAME. */
    private static final String USERNAME = "pbpublic";

    /** The Constant PASSWORD. */
    private static final String PASSWORD = "pbpublic";

    /** The Constant SELECT_SQL. */
    private static final String SELECT_SQL = "SELECT * FROM customers";

    /** The Constant INSERT_SQL. */
    private static final String INSERT_SQL = "INSERT INTO customers (CUSTOMER_NUM, POSTCODE,DISCOUNT_CODE) VALUES (999,'AA99 9ZZ','N')";

    /** The Constant UPDATE_SQL. */
    private static final String UPDATE_SQL = "UPDATE customers SET field=value WHERE key=value";

    /** The Constant DELETE_SQL. */
    private static final String DELETE_SQL = "DELETE FROM customers WHERE key=value";

    /**
     * Instantiates a new my sql dao.
     */
    public MySqlDao() {
        super(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
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
