package anton.potemkin.jdbc;

import java.sql.*;

/**
 * Created by Anton Potemkin on 25/05/2018.
 */
public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/jdbc_tutorial";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try {
            Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.out.println("Не удалось зарегестрировать драйвер");
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
//            statement.execute("INSERT INTO users(name, age, email) VALUES ('dima', 1, 'dima@mail.ru')");
//            statement.executeUpdate("DELETE FROM users WHERE name = 'anton'");
//            ResultSet res = statement.executeQuery("SELECT * FROM users");
            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('Dima', 1, 'dima@mail.ru')");
            statement.clearBatch();
            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('Elena', 60, 'elena@mail.ru')");
            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('Alexandr', 61, 'Alexandr@mail.ru')");
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Ошибка в connection");
        }


    }
}
