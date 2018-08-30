package ru.job4j.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class User.
 */
public class User {

    private Long id;
    private String passw;
    private String email;
    private Role role;
    private Address address;
    private List<MusicType> genres = new ArrayList<>();
    private Date createDate;

    public User() {
    }

    public User(final String email, final String passw, final Role role,
                final Address address, final List<MusicType> genres) {
        this.passw = passw;
        this.email = email;
        this.role = role;
        this.address = address;
        this.genres = genres;
    }

    public User(final Long id, final String email, final String passw,
                final Role role, final Address address,
                final List<MusicType> genres) {
        this.id = id;
        this.passw = passw;
        this.email = email;
        this.role = role;
        this.address = address;
        this.genres = genres;
    }

    public User(final Long id, final String email, final String passw,
                final Role role, final Address address,
                final List<MusicType> genres, final Date createDate) {
        this.id = id;
        this.passw = passw;
        this.email = email;
        this.role = role;
        this.address = address;
        this.genres = genres;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public User setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getPassw() {
        return passw;
    }

    public User setPassw(final String passw) {
        this.passw = passw;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(final String email) {
        this.email = email;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(final Role role) {
        this.role = role;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public User setAddress(final Address address) {
        this.address = address;
        return this;
    }

    public List<MusicType> getGenres() {
        return genres;
    }

    public User setGenres(final List<MusicType> genres) {
        this.genres = genres;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public User setCreateDate(final Date createDate) {
        this.createDate = createDate;
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
            && Objects.equals(this.address, that.address)
            && this.genres.containsAll(that.genres)
            && Objects.equals(this.createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, createDate, email, genres, id, passw,
            role);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("email = " + email)
            .add("passw = " + passw)
            .add("address = " + address)
            .add("role = " + role)
            .add("genres = " + genres)
            .add("createDate = " + createDate)
            .toString();
    }
}
