package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс ConnectionSQL.
 *
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 12.03.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class ConnectionSQL {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    /**
     * Конструктор.
     *
     * @param url      адрес подключения к БД.
     * @param username логин для подключения к БД.
     * @param password пароль для пдключения к БД.
     */
    public ConnectionSQL(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Геттер для получения доступа к БД.
     *
     * @return вернем ссылку на новое подключения к БД.
     */
    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Соединение установлено.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Метод для закрытия доступа к БД.
     */
    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Соединение потеряно.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}