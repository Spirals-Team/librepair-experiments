
package coaching.jdbc;

/**
 * A Data Access object for an Oracle Database.
 */
public final class OracleDao extends AbstractDao {

    /** JDBC_URL to be used. */
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /** JDBC_URL to be used. */
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:sample";

    /** USERNAME to be used. */
    private static final String USERNAME = "user";

    /** PASSWORD to be used. */
    private static final String PASSWORD = "password";

    /**
     * Instantiates a new oracle dao.
     */
    public OracleDao() {
        super(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
    }

}
