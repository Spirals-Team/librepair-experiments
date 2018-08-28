package com.roldukhine.pool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Properties;

public class ConnectionPoolTest {

    private Properties getConnectionProperties() {
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:h2:~/phonebook");
        properties.setProperty("user", "root");
        properties.setProperty("password", "");
        return properties;
    }

    @Test
    void testSingleton() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ConnectionPool connectionPool2 = ConnectionPool.getInstance();
        Assertions.assertEquals(connectionPool, connectionPool2);
    }

    @Test
    void testSetupWithNullProperties() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Assertions.assertThrows(IllegalArgumentException.class, () -> connectionPool.setup(null));
    }

    @Test
    void testGetConnection() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);
        Connection connection = connectionPool.getConnection();
        Assertions.assertNotNull(connection);

        connectionPool.release();
        connectionPool.close();
    }

    @Test
    void testGetConnectionSingleThread() {
        final ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);

        Thread thread = new Thread(() -> {
            Connection connection = connectionPool.getConnection();
            Connection connection2 = connectionPool.getConnection();
            Assertions.assertEquals(connection, connection2);
        });
        thread.start();
    }

    @Test
    void testGetConnectionMultipleThread() {
        final ConnectionPool connectionPool = ConnectionPool.getInstance();
        Properties properties = getConnectionProperties();
        connectionPool.setup(properties);

        final Connection connection = connectionPool.getConnection();

        Thread thread = new Thread(() -> {
            Connection connection2 = connectionPool.getConnection();
            Assertions.assertNotEquals(connection, connection2);
        });
        thread.start();
    }

}
