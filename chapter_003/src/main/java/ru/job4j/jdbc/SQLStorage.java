package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to work with database.
 * Set up connection URL, set up number of records and init DB.
 */
public class SQLStorage {

    private static final Logger LOG = LoggerFactory.getLogger(SQLStorage.class);

    private static final String TABLE = "TEST";
    private String url = "";
    private int number = 10;
    private Connection conn;

    /**
     * Get connection URL
     *
     * @return connection URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set connection URL
     *
     * @param url connection URL
     */
    public void setUrl(final String url) {
        this.url = url;
        LOG.info("Connection URL is: {}.", url);
    }

    /**
     * Get number of records in DB
     *
     * @return number of records
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set number of records
     *
     * @param number number of records
     */
    public void setNumber(final int number) {
        this.number = number;
        LOG.info("Table volume is: {} records.", number);
    }

    /**
     * Init DB, before usage you should set up connection URL
     * and number of records
     */
    public void initDB() {
        createTableIfRequired(TABLE);
        populateTable(TABLE, this.number);
    }

    /**
     * Get dataset
     *
     * @return dataset
     */
    public Entries getDataSet() {
        Set<Entry> entries = new HashSet<>();
        String query = String.format("SELECT * FROM %s", TABLE);
        try (Statement st = this.connect().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                entries.add(new Entry(rs.getInt("field")));
            }
            LOG.info("Got dataset from DB.");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.disconnect();
        }
        return new Entries(entries);
    }

    private Connection connect() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url);
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return conn;
    }

    private void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void createTableIfRequired(final String table) {
        String createTableQuery = String.format(
            "CREATE TABLE IF NOT EXISTS %s ("
                + "  field     INTEGER PRIMARY KEY"
                + ");", table);
        String cleanTableQuery = String.format("DELETE FROM  %s", table);
        try (Statement stmt = this.connect().createStatement()) {
            stmt.execute(createTableQuery);
            stmt.execute(cleanTableQuery);
            conn.commit();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.disconnect();
        }
    }

    private void populateTable(final String table, final int number) {
        String sql = String.format("INSERT INTO %s (field) VALUES (?);", table);
        try (PreparedStatement pstmt = this.connect().prepareStatement(sql)) {
            for (int i = 1; i <= number; i++) {
                pstmt.setString(1, String.valueOf(i));
                pstmt.executeUpdate();
            }
            conn.commit();
            LOG.info("Inserted {} records in DB.", number);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.disconnect();
        }
    }
}
