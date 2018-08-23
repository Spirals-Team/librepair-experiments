package ru.job4j.jdbc.tracker.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.jdbc.tracker.Item;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Class Database.
 */
public class Database implements AutoCloseable {
    /**
     * @param connection connection
     */
    private Connection connection;
    /**
     * @param propetries properties
     */
    private Properties properties;
    /**
     * @param Log logger
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(Database.class);

    /**
     * Constrcutor.
     */
    public Database() {
        connection = null;
        properties = new Properties();
        initProperties();
        initDB();
    }

    /**
     * Method for initializing properties.
     */
    public void initProperties() {
        File file = new File(this.getClass().getClassLoader().getResource("config.properties").getFile());
        try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for initializing work with database.
     */
    public void initDB() {
        if (!connectDB()) {
            createDB();
            connectDB();
            createTable();
        }
    }

    /**
     * Method for connecting to database.
     * @return result
     */
    public boolean connectDB() {
        boolean result = false;
        String host = properties.getProperty("db.host");
        String login = properties.getProperty("db.login");
        String password = properties.getProperty("db.password");

        try {
            connection = DriverManager.getConnection(host, login, password);
            if (connection != null) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            return result;
        }
        return result;
    }

    /**
     * Method for creating database.
     */
    private void createDB() {
        String host = properties.getProperty("db.postgres");
        String login = properties.getProperty("db.login");
        String password = properties.getProperty("db.password");

        try (Connection conn = DriverManager.getConnection(host, login, password)) {
            if (conn != null) {
                conn.prepareStatement(properties.getProperty("createDB")).execute();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for create table.
     */
    private void createTable() {
        try {
            connection.prepareStatement(properties.getProperty("createTable")).execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Add in database.
     * @param item item item for adding
     */
    public void add(Item item) {
        try (PreparedStatement ps = connection.prepareStatement("insert into items(name, description, create_date) values (?, ?, ?)")) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setTimestamp(3, new Timestamp(item.getCreate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        item.setId(takeId());
    }

    /**
     * update in database.
     * @param item item for update
     */
    public void update(Item item) {
        int id = item.getId();
        try (PreparedStatement ps = connection.prepareStatement(properties.getProperty("update"))) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setTimestamp(3, new Timestamp(item.getCreate()));
            ps.setInt(4, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * method for deleting from databse.
     * @param item item for delete
     */
    public void delete(Item item) {
        int id = item.getId();
        try (PreparedStatement ps = connection.prepareStatement(properties.getProperty("delete"))) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for finding all in database.
     */
    public void findAll() {
        try (PreparedStatement ps = connection.prepareStatement(properties.getProperty("findAll")); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(String.format("%d %s %s ", rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for find by name.
     * @param name name for find
     */
    public void findByName(String name) {
        try (PreparedStatement ps = connection.prepareStatement(properties.getProperty("findByName"))) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(String.format("%d %s %s ", rs.getInt("id"), rs.getString("name"), rs.getString("description")));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for find by id.
     * @param id id for find
     * @return result
     */
    public String findById(int id) {
        String result = null;
        try (PreparedStatement ps = connection.prepareStatement(properties.getProperty("findById"))) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(String.format("%d %s %s ", rs.getInt("id"), rs.getString("name"), rs.getString("description")));
                    result = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Method size.
     * @return current size a database
     */
    public int size() {
        int result = 0;
        try (PreparedStatement ps = connection.prepareStatement(properties.getProperty("size")); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Method for take id.
     * @return id
     */
    private int takeId() {
        int result = 0;
        try (PreparedStatement ps = connection.prepareStatement("select MAX(id) from items"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Method close.
     * @throws Exception exception
     */
    @Override
    public void close() throws Exception {
        connection.close();
    }
}
