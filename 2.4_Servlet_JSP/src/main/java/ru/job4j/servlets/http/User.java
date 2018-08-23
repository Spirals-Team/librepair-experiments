package ru.job4j.servlets.http;

import java.time.LocalDate;

/**
 * Class User.
 * @author ifedorenko
 * @since 27.07.2018
 */
public class User {
    /**
     * user's id
     */
    private int id;
    /**
     * user's name
     */
    private String name;
    /**
     * user's login
     */
    private String login;
    /**
     * user's email
     */
    private String email;
    /**
     * user's create date
     */
    private LocalDate createDate;
    /**
     * user's role
     */
    private String role;
    /**
     * user's password
     */
    private String password;

    /**
     * Constructor for User. Used for add in the db.
     * @param name user's name
     * @param login user's login
     * @param email user's email
     * @param role user's role
     * @param password user's password
     */
    public User(String name, String login, String email, String role, String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    /**
     * Constructor for User. Used for view on the page.
     * @param id user's id from db
     * @param name user's name
     * @param login user's login
     * @param email user's email
     * @param role user's role
     * @param password user's password
     * @param createDate user's create date from db
     */
    public User(int id, String name, String login, String email, String role, String password, LocalDate createDate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = createDate;
        this.role = role;
        this.password = password;
    }

    /**
     * Getter for id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for name.
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for email.
     * @param email new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for email.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for login.
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Getter for role.
     * @return users's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role.
     * @param role user's role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Getter for password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for password.
     * @return password.
     */
    public LocalDate getCreateDate() {
        return createDate;
    }
}
