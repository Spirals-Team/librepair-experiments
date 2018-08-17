package ru.job4j.jdbc.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Class StoreSql.
 */
public class StoreSQL implements AutoCloseable {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreSQL.class);
    /**
     * @param table table name
     */
    private final String table = "entry";
    /**
     * @param properties properties
     */
    private final Properties properties;
    /**
     * @param connection connection
     */
    private Connection connection;

    /**
     * Constructor.
     * @param properties properties
     */
    public StoreSQL(Properties properties) {
        this.properties = properties;
        initDB();
    }

    /**
     * Method for init db.
     */
    private void initDB() {
        connectDB();
        if (!checkTable()) {
            createTable();
        }
    }

    /**
     * Method for connect to db.
     */
    private void connectDB() {
        try {
            connection = DriverManager.getConnection(properties.getProperty("db.sqllite"));
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * method for checking the existence of a table.
     * @return result
     */
    private boolean checkTable() {
        boolean result = false;
        try (PreparedStatement ps = connection.prepareStatement("SELECT count(name) FROM sqlite_master WHERE type=? AND name=?")) {
            ps.setString(1, "table");
            ps.setString(2, table);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * method for creating a table if it does not exist.
     */
    private void createTable() {
        try (PreparedStatement ps = connection.prepareStatement("create table " + table + " (field INTEGER)")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for generate n values in db.
     * @param n value
     */
    public void generate(int n) {
        this.delete();
        try (PreparedStatement ps = connection.prepareStatement("insert into " + table + " (field) values (?)")) {
            for (int i = 1; i != n + 1; i++) {
                ps.setInt(1, i);
                ps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error(e1.getMessage(), e1);
            }
        }
    }

    /**
     * Method for delete all values in table.
     */
    public void delete() {
        try (PreparedStatement ps = connection.prepareStatement("delete from entry")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for getting all values from db.
     * @return list
     */
    public List<Field> getAll() {
        List<Field> list = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Field(rs.getInt("field")));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Method close.
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
