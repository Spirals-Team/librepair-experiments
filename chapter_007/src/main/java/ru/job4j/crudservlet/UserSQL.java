package ru.job4j.crudservlet;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserSQL {
    private final static Logger LOG = Logger.getLogger(UserSQL.class);
    private Connection connection;
    private PreparedStatement ps;

    public UserSQL() {
        createTable();
    }

    private void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/crud",
                    "postgres",
                    "Qwerty123");
        } catch (ClassNotFoundException e) {
            LOG.error(String.format("Problem in block connect: %s", e));
        } catch (SQLException e) {
            LOG.error(String.format("Problem in block connect: %s", e));
        }

    }

    private void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                LOG.error(String.format("Problem closed Connection: %s", e));
            }
        }
        if (ps != null) {
            try {
                ps.close();
                ps = null;
            } catch (SQLException e) {
                LOG.error(String.format("Problem closed PreparedStatement: %s", e));
            }
        }
    }

    public void createTable() {
        connect();
        try {
            ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS crud (id serial PRIMARY KEY, name CHARACTER VARYING(100) NOT NULL, login CHARACTER VARYING(100) NOT NULL, email CHARACTER VARYING(100) NOT NULL, createdate TIMESTAMP NOT NULL);");
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(String.format("Problem create table: %s", e));
        }
        disconnect();
    }

    public void addUser(User user) {
        connect();
        try {
            ps = connection.prepareStatement("INSERT INTO crud (name, login, email, createdate) VALUES (?, ?, ?, ?);");
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, new Timestamp(user.getCreatedate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(String.format("Problem add user: %s", e));
        }
        disconnect();
    }

    public void updateUser(String id, User user) {
        connect();
        try {
            ps = connection.prepareStatement("UPDATE crud SET name = ?, login = ? , email = ?, createdate = ? WHERE id = ?;");
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, new Timestamp(user.getCreatedate().getTime()));
            ps.setInt(5, Integer.valueOf(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(String.format("Problem update user: %s", e));
        }
        disconnect();
    }

    public void delUser(User user) {
        connect();
        try {
            ps = connection.prepareStatement("DELETE FROM crud WHERE name = ? and login = ? and email = ? and createdate = ?;");
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, new Timestamp(user.getCreatedate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(String.format("Problem del(user): %s", e));
        }
        disconnect();
    }

    public void delUser(String id) {
        connect();
        try {
            ps = connection.prepareStatement("DELETE FROM crud WHERE id = ?;");
            ps.setInt(1, Integer.valueOf(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(String.format("Problem del(id): %s", e));
        }
        disconnect();
    }

    public List<User> getUsers() {
        connect();
        List<User> list = new LinkedList<>();
        try {
            ps = connection.prepareStatement("SELECT * FROM crud;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setLogin(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setCreatedate(new Date(rs.getTimestamp(5).getTime()));
                list.add(user);
            }
        } catch (SQLException e) {
            LOG.error(String.format("Problem get list user: %s", e));
        }
        disconnect();
        return list;
    }
}
