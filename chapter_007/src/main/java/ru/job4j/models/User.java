package ru.job4j.models;

public class User {
    private int id;
    private String name;
    private String login;

    public User() {
    }

    public User(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return name + login;
    }
}
