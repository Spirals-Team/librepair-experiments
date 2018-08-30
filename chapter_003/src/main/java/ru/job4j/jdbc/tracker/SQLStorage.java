package ru.job4j.jdbc.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL Storage.
 *
 * @version $Id$
 * @since 0.1
 */
public class SQLStorage {

    private static final Logger LOG = LoggerFactory.getLogger(SQLStorage.class);

    private Connector conn = new Connector();
    private Config conf = new Config();

    /**
     * Init DB
     */
    public void initDB() {
        createTableIfRequired(conf.getQuery("createTableIfNotExist"));
    }

    private void createTableIfRequired(final String createTableQuery) {
        try (Statement stmt = conn.connect().createStatement()) {
            stmt.execute(createTableQuery);
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
            this.conn.disconnect();
        }
    }

    /**
     * Populate DB
     *
     * @param populateDB query to populate
     */
    public void populateTable(final String populateDB) {
        try (PreparedStatement pstmt = conn.connect().prepareStatement(populateDB)) {
            pstmt.setString(1, "buy");
            pstmt.setString(2, "do shopping");
            pstmt.executeUpdate();
            conn.connect().commit();
            LOG.info("Insert dummy record in DB.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.conn.disconnect();
        }
    }

    /**
     * Delete all records from DB
     */
    public void dropTable() {
        try (Statement stmt = conn.connect().createStatement()) {
            stmt.execute(conf.getQuery("deleteTable"));
            conn.connect().commit();
            LOG.info("Delete all from table item.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.conn.disconnect();
        }
    }
}
