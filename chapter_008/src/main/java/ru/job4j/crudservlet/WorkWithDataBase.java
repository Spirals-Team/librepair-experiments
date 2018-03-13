package ru.job4j.crudservlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class WorkWithDataBase {

    private Connection conn;

    WorkWithDataBase(Connection conn) {
        this.conn = conn;
    }

    void addUser(User user) {
        this.createTablesIfNotExist();
        String query = "insert into users (name, login, email, createDate) values ('" + user.name
                + "', '" + user.login + "', '" + user.email
                + "', '" + user.createDate + "');";
        try (Statement st = conn.createStatement()) {
            st.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleleUsersByLogin(String login) {
        this.createTablesIfNotExist();
        String query = "delete from users as u where u.login = '" + login + "'";
        try (Statement st = conn.createStatement()) {
            st.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleleALLUsers() {
        this.createTablesIfNotExist();
        String query = "delete from users";
        try (Statement st = conn.createStatement()) {
            st.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<User> getUsers(String login) {

        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users AS u WHERE u.login = '" + login + "'";

        this.createTablesIfNotExist();

        try (Statement st = this.conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.name = rs.getString("name");
                user.login = rs.getString("login");
                user.email = rs.getString("email");
                user.createDate = rs.getTimestamp("createDate").toLocalDateTime();

                users.add(user);
            }
        } catch (SQLException e) {
        }
        return users;
    }

    Integer getQuontityOfUsers() {

        Integer quontityOfUsers = 0;
        String query = "select count(*) as quontitity from users";

        try (Statement st = this.conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
               quontityOfUsers = rs.getInt("quontitity");
            }
        } catch (SQLException e) {
        }
        return quontityOfUsers;
    }

    void editUser(String name, String login, String email) {
        this.createTablesIfNotExist();
        String query = "update users set name = '" + name + "', email = '" + email + "' where login = '" + login + "'";
        try (Statement st = conn.createStatement()) {
            st.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createTableUsersIfNotExist() {
        ResultSet rs = readFromBase("select * from users");
        if (rs == null) {
            try (Statement st = this.conn.createStatement()) {
                st.execute("create table users(\n"
                        + "name varchar,\n"
                        + "login varchar,\n"
                        + "email varchar,\n"
                        + "createDate timestamp\n"
                        + ");");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void createTablesIfNotExist() {
        this.createTableUsersIfNotExist();
    }

    ResultSet readFromBase(String query) {
        ResultSet rs = null;
        try (Statement st = this.conn.createStatement()) {
            rs = st.executeQuery(query);
        } catch (SQLException e) {
        }
        return rs;
    }
}