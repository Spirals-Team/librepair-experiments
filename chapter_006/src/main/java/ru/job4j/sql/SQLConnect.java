package ru.job4j.sql;

import java.sql.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SQLConnect {
    private Connection conn = null;

    public SQLConnect(String url, String username, String password, Boolean autoCommit) {
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
