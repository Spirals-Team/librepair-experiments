package ru.job4j.sqlru.bd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 17.04.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class ConnectionSQL {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionSQL.class);
    private Connection connection;

    /**
     * Конструктор.
     *
     * @param url      адрес подключения к БД.
     * @param username логин для подключения к БД.
     * @param password пароль для пдключения к БД.
     */
    public ConnectionSQL(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Геттер для получения доступа к БД.
     *
     * @return вернем ссылку на новое подключения к БД.
     */
    public Connection getConnection() {
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
            LOG.error(e.getMessage(), e);
        }
    }
}
