package ru.job4j.servlets.http;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class DBStore.
 *
 * @author ifedorenko
 * @since 08.08.2018
 */
public class DBStore implements Store {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DBStore.class);
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DBStore INSTANCE = new DBStore();
    private final Properties properties;
    private final User root = new User("root", "root", "root", "admin", "root");;

    /**
     * Constructor.
     */
    private DBStore() {
        properties = initProperties();
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl(properties.getProperty("db.host"));
        SOURCE.setUsername(properties.getProperty("db.login"));
        SOURCE.setPassword(properties.getProperty("db.password"));
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        this.checkRoot();
    }

    /**
     * Method for initializing properties.
     * @return properties
     */
    private Properties initProperties() {
        Properties temp = new Properties();
        File file = new File(this.getClass().getClassLoader().getResource("web.properties").getFile());
        try (FileInputStream fis = new FileInputStream(file)) {
            temp.load(fis);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return temp;
    }

    /**
     * Method for checking user's root in db
     */
    private void checkRoot() {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from users where login = ?")) {
            ps.setString(1, root.getLogin());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    this.add(root);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void add(User user) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into users(login, name, email, role, password, create_date) values(?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getPassword());
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(String login, String name, String email, String role, String password) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("update users set name = ?, email = ?, role = ?, password = ? where login = ?")) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, role);
            ps.setString(4, password);
            ps.setString(5, login);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("delete from users where id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Delete user using login.
     * @param login for deleting
     */
    public void delete(String login) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("delete from users where login = ?")) {
            ps.setString(1, login);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from users");
             ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("login"),
                            rs.getString("email"),
                            rs.getString("role"),
                            rs.getString("password"),
                            rs.getDate("create_date").toLocalDate()));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return users;
        }
    }


    @Override
    public User findById(int id) {
        User user = null;
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from users where id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    user = new User(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("login"),
                            rs.getString("email"),
                            rs.getString("role"),
                            rs.getString("password"),
                            rs.getDate("create_date").toLocalDate());
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return user;
        }
    }

    @Override
    public User findByLogin(String login) {
        User user = null;
            try (Connection connection = SOURCE.getConnection();
                 PreparedStatement ps = connection.prepareStatement("select * from users where login = ?")) {
                ps.setString(1, login);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        user = new User(rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("login"),
                                rs.getString("email"),
                                rs.getString("role"),
                                rs.getString("password"),
                                rs.getDate("create_date").toLocalDate());
                    }
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
            return user;
        }
    }

    /**
     * Getter for INSTANCE.
     * @return DBStore instance
     */
    public static DBStore getInstance() {
        return INSTANCE;
    }
}
