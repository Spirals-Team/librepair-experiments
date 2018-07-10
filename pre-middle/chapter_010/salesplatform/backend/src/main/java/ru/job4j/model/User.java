package ru.job4j.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represent User class.
 */
public class User {

    private Long id;
    private String email;
    private String passw;
    private Role role;
    private String phone;

    public User() {
    }

    public User(final Long id) {
        this.id = id;
    }

    public User(final String email, final String passw) {
        this.email = email;
        this.passw = passw;
    }

    public User(final String email, final String passw, final String phone) {
        this.email = email;
        this.passw = passw;
        this.phone = phone;
    }

    public User(final String email, final String passw, final Role role,
                final String phone) {
        this.email = email;
        this.passw = passw;
        this.role = role;
        this.phone = phone;
    }

    public User(final Long id, final String email, final String passw,
                final Role role, final String phone) {
        this.id = id;
        this.email = email;
        this.passw = passw;
        this.role = role;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public User setPhone(final String phone) {
        this.phone = phone;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(final Role role) {
        this.role = role;
        return this;
    }

    public boolean isEmpty() {
        return this.id == null;
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
            && Objects.equals(this.passw, that.passw)
            && Objects.equals(this.role, that.role)
            && Objects.equals(this.phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, passw, role, phone);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("email = " + email)
            .add("passw = " + passw)
            .add("role = " + role)
            .add("phone = " + phone)
            .toString();
    }
}
