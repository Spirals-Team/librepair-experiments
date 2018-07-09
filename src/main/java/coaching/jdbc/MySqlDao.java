
package coaching.jdbc;

/**
 * A Data Access object for a MySQL database.
 */
public class MySqlDao extends AbstractDao {

    /** JDBC_DRIVER to be used. */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /** JDBC_URL to be used. */
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student";

    /** USER to be used. */
    private static final String USERNAME = "root";

    /** PASSWORD to be used. */
    private static final String PASSWORD = "root";

    /**
     * Instantiates a new my sql dao.
     */
    public MySqlDao() {
        super(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
    }

}
