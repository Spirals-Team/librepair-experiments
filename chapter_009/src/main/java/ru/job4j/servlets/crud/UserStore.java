package ru.job4j.servlets.crud;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.servlets.application.model.User;
import ru.job4j.servlets.application.service.SQLquery;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 08.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class UserStore {

    private static final Logger LOG = LogManager.getLogger(UserStore.class);
    private static final UserStore INSTANCE = new UserStore();
    private Connection connection;
    private String login;
    private String password;
    private String url;
    private String file = "settings.properties";

    private UserStore() {
        try {
            initParam();
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, login, password);
            createTable();
            LOG.debug("Соединение с бд установлено.");
        } catch (SQLException | ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    public void createTable() {
        try (final Statement st = this.connection.createStatement()) {
            st.executeUpdate(SQLquery.CREATE_TABLE_USERS);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    private void initParam() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(file)) {
            Properties pr = new Properties();
            pr.load(in);
            this.url = pr.getProperty("db.url");
            this.login = pr.getProperty("db.user");
            this.password = pr.getProperty("db.password");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    public static UserStore getInstance() {
        return INSTANCE;
    }

    /**
     * Метод добавляет нового пользователя в бд.
     * @param user пользователь.
     */
    public void addNewUser(User user) {
        try (PreparedStatement ps = this.connection.prepareStatement(SQLquery.ADD_NEW_USER)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, new Timestamp(user.getCreateDate().getTimeInMillis()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Метод находит пользователя по логину.
     * @param login логин.
     * @return пользователь или null если такого нет в бд.
     */
    public User getUserByLogin(String login) {
        User user = null;
        try (PreparedStatement ps = this.connection.prepareStatement(SQLquery.GET_USER_BY_LOGIN)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    user = new User();
                    user.setName(rs.getString("name"));
                    user.setLogin(rs.getString("login"));
                    user.setEmail(rs.getString("email"));
                    long time = rs.getTimestamp("date").getTime();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(time);
                    user.setCreateDate(calendar);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return  user;
    }

    /**
     * Метод обновляет данные пользователя.
     * @param oldLogin логин до обновления.
     * @param user пользователь с обновленными данными.
     * @return 1 если пользователь обновлен или 0 если такого нет в бд.
     **/
    public int updateUser(String oldLogin, User user) {
        int i = 0;
        try (PreparedStatement ps = this.connection.prepareStatement(SQLquery.UPDATE_USER)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setString(4, oldLogin);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return i;
    }

    /**
     * Метод удаляет данные пользователя из бд.
     * @param login логин.
     * @return 0 если такого нет в бд или 1 если данные удалены.
     */
    public int deleteUser(String login) {
        int i = 0;
        try (PreparedStatement ps = this.connection.prepareStatement(SQLquery.DELETE_USER)) {
            ps.setString(1, login);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return i;
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                   LOG.error(e.getMessage(), e);
                }
            }
        }
    }
}
