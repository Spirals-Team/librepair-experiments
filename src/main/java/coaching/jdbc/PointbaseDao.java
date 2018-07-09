/**
 * Data Access Object Template.
 *
 * Created on 07 September 2004, 10:38
 * @author martin.spamer
 */

package coaching.jdbc;

/**
 * A Data Access object for Pointbase Database.
 */
public final class PointbaseDao extends AbstractDao {

    /** JDBC_DRIVER to be used. */
    private static final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";

    /** JDBC_URL to be used. */
    private static final String JDBC_URL = "jdbc:pointbase:server://localhost/sample";

    /** USERNAME to be used. */
    private static final String USERNAME = "PBPUBLIC";

    /** PASSWORD to be used. */
    private static final String PASSWORD = "PBPUBLIC";

    /**
     * Instantiates a DAO for a point base customer.
     */
    public PointbaseDao() {
        super(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
    }

}
