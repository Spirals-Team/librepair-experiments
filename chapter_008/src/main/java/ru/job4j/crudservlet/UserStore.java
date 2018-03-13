package ru.job4j.crudservlet;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDateTime;

public class UserStore {

    private static final UserStore INSTANCE = new UserStore();
    Connection connection;
    WorkWithDataBase workWithDataBase;

    private UserStore() {
        this.getInitConnection();
        workWithDataBase = new WorkWithDataBase(connection);
        workWithDataBase.deleleALLUsers();
    }

    public static UserStore getInstance() {
        return INSTANCE;
    }

    private Connection getInitConnection() {
        MyConnection myConnection = new MyConnection();
        myConnection.initConnection();
        connection = myConnection.conn;
        return connection;
    }

    private void addSql(User user) {
        workWithDataBase.addUser(user);
    }

    private ArrayList<User> getSql(String login) {
        return  workWithDataBase.getUsers(login);
    }

    public void add(String name, String login, String email) {
        if (this.get(login).equals("")) {

            User user = new User();
            user.name = name;
            user.login = login;
            user.email = email;
            user.createDate = LocalDateTime.now();

            addSql(user);
        }
    }

    public String get(String login) {

        ArrayList<User> users = getSql(login);

        String listOfUsers = "";
        Iterator iterator = users.listIterator();
        while (iterator.hasNext()) {
            User currUser = (User) iterator.next();
            if (currUser.login.equals(login)) {
            listOfUsers = listOfUsers + "\n" + currUser.toString();
            }
        }
        return listOfUsers;
    }

    public void delete(String login) {
        workWithDataBase.deleleUsersByLogin(login);
    }

    public void edit(String name, String login, String email) {
        this.editSql(name, login, email);
    }

    public void editSql(String name, String login, String email) {
       workWithDataBase.editUser(name, login, email);
    }

    public Integer getQuontityOfUsers() {
        return this.getQuontityOfUsersSql();
    }

    public Integer getQuontityOfUsersSql() {
        return workWithDataBase.getQuontityOfUsers();
    }
}