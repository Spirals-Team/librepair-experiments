/**
 * Data Access Object Template.
 *
 * Created on 07 September 2004, 10:38
 * @author martin.spamer
 */

package coaching.jdbc;

/**
 * Data Access object for Point Base sample customer table.
 */
public final class PointBaseCustomerDao extends AbstractDataAccessObject {

    /** The Constant JDBC_DRIVER. */
    private static final String JDBC_DRIVER = "com.pointbase.jdbc.jdbcUniversalDriver";

    /** The Constant JDBC_URL. */
    private static final String JDBC_URL = "jdbc:pointbase:server://localhost/sample";

    /** The Constant USER. */
    private static final String USER = "PBPUBLIC";

    /** The Constant PASSWORD. */
    private static final String PASSWORD = "PBPUBLIC";

    /** The Constant SELECT_SQL. */
    private static final String SELECT_SQL = "SELECT * FROM CUSTOMER_TBL";

    /** The Constant INSERT_SQL. */
    private static final String INSERT_SQL = "INSERT INTO CUSTOMER_TBL (CUSTOMER_NUM, POSTCODE,DISCOUNT_CODE) VALUES (999,'AA99 9ZZ','N')";

    /** The Constant UPDATE_SQL. */
    private static final String UPDATE_SQL = "UPDATE CUSTOMER_TBL SET NAME ='DataMentor' WHERE CUSTOMER_NUM=999";

    /** The Constant DELETE_SQL. */
    private static final String DELETE_SQL = "DELETE FROM CUSTOMER_TBL WHERE CUSTOMER_NUM=999";

    /**
     * Instantiates a DAO for a point base customer.
     */
    public PointBaseCustomerDao() {
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
        super.create(SELECT_SQL);
    }

    /**
     * Update.
     */
    public void update() {
        super.create(UPDATE_SQL);
    }

    /**
     * Delete.
     */
    public void delete() {
        super.create(DELETE_SQL);
    }

}
