package ru.aserdyuchenko.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class JdbcStorage {
    private final Connection connection;
    private String sqlSelect = "SELECT * FROM test";
    private String sqlCreateTable = "CREATE TABLE test (number Int)";
    private String SQL_INSERT = "INSERT INTO test VALUES(?)";
    private String SQL_DELETE = "DELETE FROM test";
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
            connection.setAutoCommit(false);
            if (!ifTableExist()) createTable();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get all data from table.
     */
    public List<Integer> get() throws SQLException {
        List<Integer> list = new ArrayList<>();
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet rs = statement.executeQuery(sqlSelect);
            while (rs.next()) {
                list.add(rs.getInt("number"));
            }
            this.connection.commit();
            rs.close();
            statement.close();
            return list;
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create table.
     */
    private void createTable() throws SQLException {
        try (Statement statement = this.connection.createStatement()) {
            statement.addBatch(sqlCreateTable);
            this.connection.commit();
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Delete all from table "test".
     */
    private void delete() throws SQLException {
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(SQL_DELETE);
            this.connection.commit();
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Add numbers in table. From 1 to N.
     * @param n - count numbers.
     */
    public void add(int n) throws SQLException {
        delete();
        try {
            final PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT);
            statement.addBatch();
            for (int i = 1; i <= n; i++) {
                statement.setInt(1, i);
                statement.executeUpdate();
            }
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
    private boolean ifTableExist() throws SQLException {
        try {
            DatabaseMetaData md = this.connection.getMetaData();
            md.getTables(null, null, "test", null);
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
            return false;
        }
    }
}