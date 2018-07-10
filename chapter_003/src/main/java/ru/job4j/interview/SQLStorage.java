package ru.job4j.interview;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL Storage.
 *
 * @version $Id$
 * @since 0.1
 */
public class SQLStorage {

    private static final Logger LOG = Logger.getLogger(SQLStorage.class);

    private Connector conn = new Connector();
    private Config conf = new Config();

    /**
     * Create table if not exist
     */
    public void initDB() {
        try (Statement stmt = conn.connect().createStatement()) {
            stmt.execute(conf.getQuery("createTableIfNotExist"));
            conn.connect().commit();
            LOG.info("Create table if required.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            conn.disconnect();
        }
    }
}
