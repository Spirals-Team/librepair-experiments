package ru.job4j.userapp;

import java.sql.Date;
import java.util.StringJoiner;

/**
 * Class represent user entity
 */
public class User {

    private Integer id;
    private String name;
    private String login;
    private String passw;
    private String email;
    private String region;
    private String country;
    private Role role;
    private Date createDate;

    public User() {
    }

    public User(final String name, final String login, final String passw,
                final String email, final String region, final String country,
                final Role role) {
        this.name = name;
        this.login = login;
        this.passw = passw;
        this.email = email;
        this.region = region;
        this.country = country;
        this.role = role;
    }

    public User(final Integer id, final String name, final String login,
                final String passw, final String email, final String region,
                final String country, final Role role) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.passw = passw;
        this.email = email;
        this.region = region;
        this.country = country;
        this.role = role;
    }

    public User(final Integer id, final String name, final String login,
                final String passw, final String email, final String region,
                final String country, final Role role, final Date createDate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.passw = passw;
        this.email = email;
        this.region = region;
        this.country = country;
        this.role = role;
        this.createDate = createDate;
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
        return (id != null ? id.equals(user.id) : user.id == null)
            && (name != null ? name.equals(user.name) : user.name == null)
            && (login != null ? login.equals(user.login) : user.login == null)
            && (passw != null ? passw.equals(user.passw) : user.passw == null)
            && (email != null ? email.equals(user.email) : user.email == null)
            && (region != null ? region.equals(user.region) : user.region == null)
            && (country != null ? country.equals(user.country) : user.country == null)
            && role == user.role
            && (createDate != null
            ? createDate.equals(user.createDate) : user.createDate == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (passw != null ? passw.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("name = " + name)
            .add("login = " + login)
            .add("passw = " + passw)
            .add("email = " + email)
            .add("region = " + region)
            .add("country = " + country)
            .add("role = " + role)
            .add("createDate = " + createDate)
            .toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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

    public String getPassw() {
        return passw;
    }

    public void setPassw(final String passw) {
        this.passw = passw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }
}
