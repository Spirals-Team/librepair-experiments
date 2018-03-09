package ru.aserdyuchenko.crudServlet;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class Storage {
    private final Connection connection;
    private String SQL_SELECT = "SELECT * FROM users";
    private String SQL_CREATE_TABLE = "CREATE TABLE users (login character varying (50), name character varying (50), email character varying (50), createDate character varying (50))";
    private String SQL_INSERT = "INSERT INTO users VALUES(?, ?, ?, ?)";
    private String SQL_DELETE = "DELETE FROM users WHERE login = ?";
    private String SQL_UPDATE = "UPDATE users SET name=?, email=?, createDate=? WHERE login=?";

    /**
     * Constructor.
     */
    public Storage() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try {

            final Settings settings = Settings.getInstance();
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "root");
            connection.setAutoCommit(false);
            if (!ifTableExist()) createTable();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get all data from table.
     */
    public Map<String, User> get() throws SQLException {
        Map<String, User> users = new HashMap<>();
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet rs = statement.executeQuery(SQL_SELECT);
            while (rs.next()) {
                users.put(rs.getString("login"), new User(rs.getString("name"), rs.getString("email"), rs.getString("createDate")));
            }
            this.connection.commit();
            rs.close();
            statement.close();
            return users;
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update user.
     * @throws SQLException
     */
    public void update(String name, String email, String createDate, String login) throws SQLException {
        try {
            final PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE);
            statement.addBatch();
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, createDate);
            statement.setString(4, login);
            statement.executeUpdate();
            this.connection.commit();
            statement.close();
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Create table.
     */
    public void createTable() throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
            statement.addBatch(SQL_CREATE_TABLE);
            this.connection.commit();
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Add user.
     */
    public void add(String login, String name, String email, String createDate) throws SQLException {
        try {
            final PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT);
            statement.addBatch();
            statement.setString(1, login);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, createDate);
            statement.executeUpdate();
            this.connection.commit();
            statement.close();
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Check if table exist.
     */
    public boolean ifTableExist() throws SQLException {
        try {
            DatabaseMetaData md = this.connection.getMetaData();
            md.getTables(null, null, "users", null);
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete all from table "test".
     */
    public void delete(String login) throws SQLException {
        try {
            final PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE);
            statement.addBatch();
            statement.setString(1, login);
            statement.executeUpdate();
            this.connection.commit();
            statement.close();
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }
}
