package ru.aserdyuchenko.servlet;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class User {
    private String login;
    private String name;
    private String email;
    private String createDate;
    private String password;
    private String role;

    public User(String login, String name, String email, String createDate, String password, String role) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.createDate = createDate;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
