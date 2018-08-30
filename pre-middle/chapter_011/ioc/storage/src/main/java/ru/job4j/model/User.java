package ru.job4j.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class User.
 */
public class User {

    private Long id;
    private String email;
    private String passw;

    public User() {
    }

    public User(final Long id) {
        this.id = id;
    }

    public User(final String email, final String passw) {
        this.email = email;
        this.passw = passw;
    }

    public User(final Long id, final String email, final String passw) {
        this.id = id;
        this.email = email;
        this.passw = passw;
    }

    public Long getId() {
        return id;
    }

    public User setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getPassw() {
        return passw;
    }

    public User setPassw(final String passw) {
        this.passw = passw;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User that = (User) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.email, that.email)
            && Objects.equals(this.passw, that.passw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, passw);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("email = " + email)
            .add("passw = " + passw)
            .toString();
    }
}
