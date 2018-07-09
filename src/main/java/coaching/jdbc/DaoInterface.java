
package coaching.jdbc;

/**
 * Data Access Object Interface.
 */
public interface DaoInterface {

    /** SELECT_SQL. */
    static final String SELECT_SQL = "SELECT (*) FROM TABLE";

    /** INSERT_SQL. */
    static final String INSERT_SQL = "INSERT INTO TABLE (ID, FIELD, DATA) VALUES (999,'...','...')";

    /** UPDATE_SQL. */
    static final String UPDATE_SQL = "UPDATE TABLE SET FIELD = 'Value' WHERE ID=999";

    /** DELETE_SQL. */
    static final String DELETE_SQL = "DELETE FROM TABLE WHERE ID=999";

    /**
     * Sets the driver.
     *
     * @param driver the driver
     * @return the dao interface
     */
    DaoInterface setDriver(final String driver);

    /**
     * Sets the url.
     *
     * @param url the url
     * @return the dao interface
     */
    DaoInterface setUrl(final String url);

    /**
     * Sets the username.
     *
     * @param username the username
     * @return the dao interface
     */
    DaoInterface setUsername(final String username);

    /**
     * Sets the password.
     *
     * @param password the password
     * @return the dao interface
     */
    DaoInterface setPassword(final String password);

    /**
     * Create record.
     *
     * @param sql
     *            the sql
     * @return this as fluent interface.
     */
    DaoInterface create(final String sql);

    /**
     * Read record.
     *
     * @param sql
     *            the sql
     * @return this as fluent interface.
     */
    DaoInterface read(final String sql);

    /**
     * Update record.
     *
     * @param sql
     *            the sql
     * @return this as fluent interface.
     */
    DaoInterface update(final String sql);

    /**
     * Delete record.
     *
     * @param sql
     *            the sql
     * @return this as fluent interface.
     */
    DaoInterface delete(final String sql);

}
