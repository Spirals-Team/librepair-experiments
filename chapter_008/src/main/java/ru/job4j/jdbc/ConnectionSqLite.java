package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class ConnectionSqLite {
    //  private final String driver = "org.sqlite.JDBC";
    //protected static final String url = ;
     private  String url;

    private Connection connection;
    public Connection getConnection() {
        if (System.getProperty("os.name").equals("Linux")) {
            this.url = "jdbc:sqlite:/home/andrei/java.db";
        } else {
            this.url =  "jdbc:sqlite:D:/sqlite/java.db";
        }
        getConnect();
        return connection;
    }
    private void getConnect() {
        try {
            //  Class.forName(driver);
            connection = DriverManager.getConnection(url);
            System.out.println("Соединение установлено.");
            // } catch (ClassNotFoundException e) {
            //  e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
