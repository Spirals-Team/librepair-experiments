package ru.job4j.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Objects;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 25.04.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class User {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);
    private int id;
    private String name;
    private String login;
    private String email;
    private Calendar createDate;
    private String password;
    private String countries;
    private String citi;

    User(final UserBuilder userBuilder) {
        this.id = userBuilder.getId();
        this.name = userBuilder.getName();
        this.login = userBuilder.getLogin();
        this.email = userBuilder.getEmail();
        this.createDate = userBuilder.getCreateDate();
        this.password = userBuilder.getPassword();
        this.countries = userBuilder.getCountries();
        this.citi = userBuilder.getCiti();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(createDate, user.createDate)
                && Objects.equals(password, user.password)
                && Objects.equals(citi, user.citi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, createDate, password, citi);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", createDate=" + createDate
                + ", password='" + password + '\''
                + ", countries='" + countries + '\''
                + ", citi='" + citi + '\''
                + '}';
    }
}
