package ru.job4j.crud;

import java.sql.Date;

/**
 * Class represent user entity
 */
public class User {

    private String name;
    private String login;
    private String email;
    private Date createDate;

    public User() {
    }

    public User(final String name, final String login, final String email) {
        this.name = name;
        this.login = login;
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return (name != null ? name.equals(user.name) : user.name == null)
            && (login != null ? login.equals(user.login) : user.login == null)
            && (email != null ? email.equals(user.email) : user.email == null)
            && (createDate != null
            ? createDate.equals(user.createDate) : user.createDate == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("User{name='%s', login='%s', email='%s', "
            + "createDate=%s}", name, login, email, createDate);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }
}
