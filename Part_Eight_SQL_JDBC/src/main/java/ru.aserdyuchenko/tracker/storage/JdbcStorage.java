package ru.aserdyuchenko.tracker.storage;

import ru.aserdyuchenko.JDBC.Settings;
import ru.aserdyuchenko.tracker.models.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class JdbcStorage {
    private final Connection connection;

    /**
     * Constructor.
     */
    public JdbcStorage() {
        try {
            final Settings settings = Settings.getInstance();
            this.connection = DriverManager.getConnection(
                    settings.value("jdbc.url"),
                    settings.value("jdbc.username"),
                    settings.value("jdbc.password"));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get all data from table.
     */
    public List<Item> get() throws SQLException {
        List<Item> list = new ArrayList<>();
        try (final Statement statement = this.connection.createStatement();
            final ResultSet rs = statement.executeQuery("SELECT * FROM storage")) {
            while (rs.next()) {
                list.add(new Item(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("createTime")
                        ));
            }
            rs.close();
            statement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create table.
     */
    private void createTable() throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("CREATE TABLE storage (id VARCHAR(25), name VARCHAR(25), description VARCHAR(50), createTime INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all from table "storage".
     */
    public void delete(String itemID) throws SQLException {
        try (final PreparedStatement statement = this.connection.prepareStatement("DELETE FROM storage WHERE id = ?")) {
            statement.setString(1, itemID);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add item.
     * @param item - item.
     */
    public void add(Item item) throws SQLException {
        if (!ifTableExist()) createTable();
        try (final PreparedStatement statement = this.connection.prepareStatement("INSERT INTO storage VALUES (?, ?, ?, ?)")) {
            statement.setString(1, item.getId());
            statement.setString(2, item.getName());
            statement.setString(3, item.getDescription());
            statement.setLong(4, item.getCreate());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if table exist.
     */
    private boolean ifTableExist() {
        boolean checkResult = false;
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet resultSet = md.getTables(null, null,"storage", null);
            if (resultSet.next()) checkResult = true;
            return checkResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return checkResult;
        }
    }
}