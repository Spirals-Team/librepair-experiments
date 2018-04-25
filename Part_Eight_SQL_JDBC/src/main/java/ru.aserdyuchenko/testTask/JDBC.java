package ru.aserdyuchenko.testTask;

import java.sql.*;
import java.util.Set;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class JDBC {
    private final Connection connection;
    private String SQL_CREATE_TABLE = "CREATE TABLE offers (job_offer VARCHAR(255))";
    private String SQL_INSERT = "INSERT INTO offers VALUES(?)";
    private String SQL_DELETE = "DELETE FROM offers";

    /**
     * Constructor.
     */
    public JDBC() {
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
     * Create table.
     */
    private void createTable() throws SQLException {
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(SQL_CREATE_TABLE);
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
     * Add all offers.
     * @param list - list with offers.
     */
    public void add(Set<String> list) throws SQLException {
        delete();
        try {
            final PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT);
            statement.addBatch();
            for (String string : list) {
                statement.setString(1, string);
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
            md.getTables(null, null, "offers", null);
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
