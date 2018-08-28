package ru.job4j.model;

import java.util.Calendar;

public class UserBuilder {
    private int id;
    private String name;
    private String login;
    private String email;
    private Calendar createDate;
    private String password;
    private String countries;
    private String citi;

    public UserBuilder setId(final int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setLogin(final String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setEmail(final String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setCreateDate(final Calendar createDate) {
        this.createDate = createDate;
        return this;
    }

    public UserBuilder setPassword(final String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setCountries(final String countries) {
        this.countries = countries;
        return this;
    }

    public UserBuilder setCiti(final String citi) {
        this.citi = citi;
        return this;
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

    public String getEmail() {
        return email;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public String getPassword() {
        return password;
    }

    public String getCountries() {
        return countries;
    }

    public String getCiti() {
        return citi;
    }

    public User build() {
        return new User(this);
    }
}
