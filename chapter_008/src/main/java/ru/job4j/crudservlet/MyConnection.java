package ru.job4j.crudservlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MyConnection {

    Connection conn;
    private String url;
    private String username;
    private String password;

    private String dbDriver;

    void initConnection() {
        this.url = "jdbc:postgresql://localhost:5432/Crudervlet2512";
        this.username = "postgres";
        this.password = "password";
        this.dbDriver = "org.postgresql.Driver";
        this.conn = this.getConnection();
    }

    Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                try {
                    conn = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}