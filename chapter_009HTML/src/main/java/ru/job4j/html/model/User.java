package ru.job4j.html.model;


import java.sql.Timestamp;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.02.18;
 * @version $Id$
 * @since 0.1
 */
public class User {
    private String login;
    private String name;
    private String email;
    private String password;
    private String countryId;
    private String townId;
    private String role;
    private Timestamp data;

    @Override
    public String toString() {
        return "User{"
                + "login='"
                + login + '\''
                + ", name='"
                + name + '\''
                + ", email='"
                + email
                + '\''
                + ", password='"
                + password
                + '\''
                + ", countryId='"
                + countryId
                + '\''
                + ", townId='"
                + townId
                + '\''
                + ", role='"
                + role
                + '\''
                + ", data="
                + data
                + '}';
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public User(String login, String name, String email,
    String password, String countryId, String townId, String role) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
        this.countryId = countryId;
        this.townId = townId;
        this.role = role;
    }
}
