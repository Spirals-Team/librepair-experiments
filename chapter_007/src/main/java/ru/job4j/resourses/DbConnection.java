package ru.job4j.resourses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection connection = null;

    private DbConnection() {

    }

    public static synchronized Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/java_a_to_z";
        String user = "clydeside";
        String password = "root";
//        try {
//            if (connection == null) {
                connection = DriverManager.getConnection(url, user, password);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return connection;
    }
}
