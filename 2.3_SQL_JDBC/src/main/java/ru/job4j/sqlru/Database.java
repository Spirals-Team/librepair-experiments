package ru.job4j.sqlru;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Stack;

/**
 * Class Database.
 */
public class Database {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    /**
     * @param properties properties
     */
    private final Properties properties;
    /**
     * @param connection connection
     */
    private Connection connection;

    /**
     * Constrcutor.
     * @param properties properties
     */
    public Database(Properties properties) {
        this.properties = properties;
        initDB();
    }

    /**
     * Method for initializing work with database.
     */
    public void initDB() {
        connectDB("db.postgres", "db.login", "db.password");
        if (!checkDB()) {
            createDB();
            createTable();
        } else {
            closeCon();
        }
    }

    /**
     * Method for insert values from stack to db.
     * @param stack stack with parsing result
     */
    public void insertDB(Stack<Vacancy> stack) {
        connectDB("db.host", "db.login", "db.password");
        removeDuplicate(stack);
        try (PreparedStatement ps = connection.prepareStatement("insert into job (name, url, add_date) values (?, ?, ?)")) {
            while (!stack.empty()) {
                Vacancy vacancy = stack.pop();
                ps.setString(1, vacancy.getName());
                ps.setString(2, vacancy.getUrl());
                ps.setDate(3, Date.valueOf(vacancy.getDate().toLocalDate()));
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            closeCon();
        }
    }

    /**
     * Method for connecting to database.
     * @param url url
     * @param user user
     * @param pass pass
     */
    private void connectDB(String url, String user, String pass) {
        String host = properties.getProperty(url);
        String login = properties.getProperty(user);
        String password = properties.getProperty(pass);
        try {
            connection = DriverManager.getConnection(host, login, password);
        } catch (SQLException e) {
            LOGGER.info("Wrong data for connect");
        }
    }

    /**
     * Method for checking database exist.
     * @return check result
     */
    private boolean checkDB() {
        boolean result = false;
        try (PreparedStatement ps = connection.prepareStatement("select exists(SELECT datname FROM pg_catalog.pg_database "
                + "WHERE lower(datname) = lower(?))")) {
            ps.setString(1, properties.getProperty("db.name"));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result = rs.getBoolean("exists");
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            return result;
        }
    }

    /**
     * Method for creating database.
     */
    private void createDB() {
        try {
            connection.prepareStatement("create database sqlru").execute();
        } catch (SQLException e) {
            LOGGER.info("database didn't create");
        } finally {
            closeCon();
        }
    }

    /**
     * Method for removing from stack duplicates that is in the database.
     * @param stack stack with parsing result
     */
    public void removeDuplicate(Stack<Vacancy> stack) {
        try (PreparedStatement ps = connection.prepareStatement("select id from job where url = ?")) {
            Iterator i = stack.iterator();
            while (i.hasNext()) {
                Vacancy vacancy = (Vacancy) i.next();
                ps.setString(1, vacancy.getUrl());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        i.remove();
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method size.
     * @return size by db.
     */
    public int size() {
        connectDB("db.host", "db.login", "db.password");
        int result = 0;
        try (PreparedStatement ps = connection.prepareStatement("select count(name) from job"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            closeCon();
            return result;
        }
    }


    /**
     * Method for create table.
     */
    private void createTable() {
        connectDB("db.host", "db.login", "db.password");
        try (PreparedStatement ps = connection.prepareStatement("create table job ( id serial primary key, name VARCHAR , url VARCHAR, add_date DATE)")) {
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            closeCon();
        }
    }

    /**
     * Method for closing connecting.
     */
    private void closeCon() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
