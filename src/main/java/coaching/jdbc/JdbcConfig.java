
package coaching.jdbc;

import coaching.config.Configuration;

/**
 * JDBC Configuration class.
 */
public class JdbcConfig extends Configuration {

    /** The Constant INSTANCE. */
    private static final JdbcConfig INSTANCE = new JdbcConfig();

    /**
     * Private constructor to prevent wild instantiation.
     */
    private JdbcConfig() {
        super();
    }

    /**
     * Get the instance.
     *
     * @return the instance
     */
    public static JdbcConfig getInstance() {
        return INSTANCE;
    }

    /**
     * Get the JDBC driver classname.
     *
     * @return the string
     */
    public static String driver() {
        return JdbcConfig.getInstance().valueFor("driver");
    }

    /**
     * Get the JDBC Connection URL.
     *
     * @return the string
     */
    public static String url() {
        return JdbcConfig.getInstance().valueFor("url");
    }

    /**
     * Get the username.
     *
     * @return the string
     */
    public static String username() {
        return JdbcConfig.getInstance().valueFor("username");
    }

    /**
     * Get the password.
     *
     * @return the string
     */
    public static String password() {
        return JdbcConfig.getInstance().valueFor("password");
    }

    /**
     * Get the schema name.
     *
     * @return the string
     */
    public static String schema() {
        return JdbcConfig.getInstance().valueFor("schema");
    }

    /**
     * Get the Query.
     *
     * @return the string
     */
    public static String query() {
        return JdbcConfig.getInstance().valueFor("sql.query");
    }

    /**
     * SQL Select query.
     *
     * @return the string
     */
    public static String select() {
        return JdbcConfig.getInstance().valueFor("sql.select");
    }

    /**
     * Insert.
     *
     * @return the string
     */
    public static String insert() {
        return JdbcConfig.getInstance().valueFor("sql.insert");
    }

    /**
     * Update.
     *
     * @return the string
     */
    public static String update() {
        return JdbcConfig.getInstance().valueFor("sql.update");
    }

    /**
     * Delete.
     *
     * @return the string
     */
    public static String delete() {
        return JdbcConfig.getInstance().valueFor("sql.delete");
    }
}
