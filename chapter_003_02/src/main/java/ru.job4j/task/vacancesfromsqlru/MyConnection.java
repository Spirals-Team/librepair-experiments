package ru.job4j.task.vacancesfromsqlru;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MyConnection {

    private static final Logger LOGGG = LoggerFactory.getLogger(MyConnection.class);
    Connection conn;
    private String url;
    private String username;
    private String password;

    void initConnection() {
        this.url = "jdbc:postgresql://localhost:5432/task1731";
        this.username = "postgres";
        this.password = "password";
        this.conn = this.getConnection();
    }

    Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGG.error(e.getMessage(), e);
            }
        }
        return conn;
    }
}
