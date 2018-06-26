
package coaching.jdbc;

/**
 * class CachedTable.
 */
public class CachedTable extends AbstractDataAccessObject {

    /** JDBC_DRIVER to be used. */
    private static final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";

    /** JDBC_URL to be used. */
    private static final String JDBC_URL = "jdbc:pointbase://localhost:9092/sample";

    /** USERNAME to be used. */
    private static final String USERNAME = "pbpublic";

    /** PASSWORD to be used. */
    private static final String PASSWORD = "pbpublic";

    /**
     * Instantiates a new cached table.
     */
    public CachedTable() {
        super(JDBC_DRIVER, JDBC_URL, USERNAME, PASSWORD);
    }

}
