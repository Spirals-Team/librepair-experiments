package ru.job4j.dto;

import java.sql.Date;
import java.util.Objects;
import java.util.StringJoiner;

public class UserDto {

    private Long id;
    private String email;
    private String passw;
    private Long roleId;
    private Long addressId;
    private Date createDate;

    public UserDto() {
    }

    public UserDto(final String email, final String passw, final Long roleId,
                   final Long addressId, final Date createDate) {
        this.passw = passw;
        this.email = email;
        this.roleId = roleId;
        this.addressId = addressId;
        this.createDate = createDate;
    }

    public UserDto(final Long id, final String email, final String passw,
                   final Long roleId, final Long addressId, final Date createDate) {
        this.id = id;
        this.passw = passw;
        this.email = email;
        this.roleId = roleId;
        this.addressId = addressId;
        this.createDate = createDate;
    }

    public UserDto(final Long id, final String email, final String passw,
                   final Long roleId, final Long addressId) {
        this.id = id;
        this.passw = passw;
        this.email = email;
        this.roleId = roleId;
        this.addressId = addressId;
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getPassw() {
        return passw;
    }

    public UserDto setPassw(final String passw) {
        this.passw = passw;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserDto setRoleId(final Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public Long getAddressId() {
        return addressId;
    }

    public UserDto setAddressId(final Long addressId) {
        this.addressId = addressId;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public UserDto setCreateDate(final Date createDate) {
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
        UserDto that = (UserDto) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.email, that.email)
            && Objects.equals(this.passw, that.passw)
            && Objects.equals(this.roleId, that.roleId)
            && Objects.equals(this.addressId, that.addressId)
            && Objects.equals(this.createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, passw, roleId, addressId, createDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("email = " + email)
            .add("passw = " + passw)
            .add("roleId = " + roleId)
            .add("addressId = " + addressId)
            .add("createDate = " + createDate)
            .toString();
    }
}
