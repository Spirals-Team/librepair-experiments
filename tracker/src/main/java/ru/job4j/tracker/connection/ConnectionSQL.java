package ru.job4j.tracker.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс соединения с бд.
 * @author Hincu Andrei (andreih1981@gmail.com) by 09.12.17;
 * @version $Id$
 * @since 0.1
 */
public class ConnectionSQL {
    /**
     * Имя пользователя в базе данных.
     */
    private final String user = "postgres";
    /**
     * Путь к бд.
     */
    private final String url = "jdbc:postgresql://localhost:5432/tracker";
    /**
     * Пароль к бд.
     */
    private final String password = "5432";
    /**
     * Соединение с бд.
     */
    private  Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public ConnectionSQL() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
