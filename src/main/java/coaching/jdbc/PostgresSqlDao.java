/**
 * Data Access Object Template.
 *
 * Created on 07 September 2004, 10:38
 * @author martin.spamer
 */

package coaching.jdbc;

/**
 * A Data Access object for PostgreSql Database.
 */
public final class PostgresSqlDao extends AbstractDao {

    /** JDBC_DRIVER to be used. */
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    /** JDBC_URL to be used. */
    private static final String JDBC_URL = "jdbc:postgresql://localhost";

    /** USERNAME to be used. */
    private static final String USERNAME = "postgres";

    /** PASSWORD to be used. */
    private static final String PASSWORD = "password";

    /**
     * Instantiates a DAO for a point base customer.
     */
    public PostgresSqlDao() {
        super(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
    }

}
