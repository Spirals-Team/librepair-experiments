
package coaching.jdbc;

/**
 * Data Access Object Interface.
 */
public interface DaoInterface {

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
