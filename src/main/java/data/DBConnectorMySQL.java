package data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectorMySQL {

    //Constants
    private static final String IP = "jdbc:mysql://209.97.128.24";
    private static final String PORT = "3306"; // best left empty for default port
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "class";
    private static final String DB = "gutenberg";
    private Connection conn = null;

    public DBConnectorMySQL() {
        try {
            conn = DriverManager.getConnection(IP + ":3306/" + DB, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
    }

    public Connection getConn() {
        return conn;
    }
}
