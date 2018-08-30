package ru.job4j.jdbc.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class represent connection to DB.
 *
 * @version $Id$
 * @since 0.1
 */
public class Connector {

    private static final Logger LOG = LoggerFactory.getLogger(Connector.class);

    private Connection conn;
    private Config config = new Config();

    /**
     * Get connection to DB
     *
     * @return connection
     */
    public Connection connect() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(config.getURL());
                conn.setAutoCommit(false);
                LOG.info("Create connection to { {} }.", conn.getMetaData().getURL());
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return conn;
    }

    /**
     * Close connection to DB
     */
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
