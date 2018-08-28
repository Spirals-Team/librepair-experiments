package com.roldukhine.pool;

import com.roldukhine.exception.DaoException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConnectionPool {

    private static final int DEFAULT_SIZE = 10;

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_PASSWORD = "password";

    private static final ConnectionPool instance = new ConnectionPool();
    private final BlockingQueue<Connection> pool = new LinkedBlockingQueue<>(DEFAULT_SIZE);
    private final ThreadLocal<ConnectionHolder> connectionHolder = new ThreadLocal<>();

    public static ConnectionPool getInstance() {
        logger.debug("getInstance {}", instance);
        return instance;
    }

    public void setup(Properties properties) {
        logger.debug("setup properties {}", properties);
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }

        String url = properties.getProperty(PROPERTY_URL);
        String user = properties.getProperty(PROPERTY_USER);
        String password = properties.getProperty(PROPERTY_PASSWORD);
        logger.debug("url {}, user{}, password{}", url, user, password);

        logger.debug("DEFAULT_SIZE {}", DEFAULT_SIZE);
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Connection connection = createConnection(url, user, password);
            if (connection != null) {
                pool.offer(connection);
            }
        }
    }

    private Connection createConnection(String url, String user, String password) {
        logger.debug("createConnection url {}, user {user}, password {}", url, user, password);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("return connection {}", connection);
        return connection;
    }

    public Connection getConnection() {
        logger.trace("getConnection");
        if (connectionHolder.get() == null) {
            try {
                Connection connection = pool.poll(5, TimeUnit.SECONDS);
                ConnectionHolder holder = new ConnectionHolder(connection);
                connectionHolder.set(holder);
            } catch (InterruptedException e) {
                logger.error("Error after create connection", e);
                throw new DaoException("Cannot create connection.");
            }
        }

        ConnectionHolder holder = this.connectionHolder.get();
        logger.debug("ConnectionHolder {}", holder);
        holder.incrementAndGet();
        return holder.getConnection();
    }

    public void release() {
        logger.trace("release");
        ConnectionHolder holder = connectionHolder.get();
        logger.debug("ConnectionHolder {}", holder);
        if (holder == null) {
            return;
        }

        Connection connection = holder.getConnection();
        logger.debug("connection", connection);
        if (connection == null) {
            return;
        }

        holder.getAndDecrement();
        int holderCounter = holder.getCounter();
        logger.debug("holderCounter", holderCounter);
        if (holderCounter == 0 && pool.size() < DEFAULT_SIZE) {
            try {
                logger.debug("put connection", connection);
                pool.put(connection);
            } catch (InterruptedException e) {
                throw new DaoException("Error while releasing connection.");
            }
            this.connectionHolder.remove();
        }
    }

    public void close() {
        logger.trace("close");
        try {
            Connection connection = pool.take();
            connection.close();
        } catch (SQLException | InterruptedException e) {
            logger.error("error after close pool", e);
            throw new DaoException(e);
        }
    }
}
