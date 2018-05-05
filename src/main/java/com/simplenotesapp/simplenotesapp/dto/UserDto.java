package com.simplenotesapp.simplenotesapp.dto;

import java.util.Set;

public class UserDto {

    private long id;
    private String login;
    private String name;
    private String surname;
    private String password;
    private Set<Long> notesId;

    public UserDto(final long id, final String login, final String name, final String surname, final String password, final Set<Long> notesId) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.notesId = notesId;
    }

    public UserDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Long> getNotesId() {
        return notesId;
    }

    public void setNotesId(Set<Long> notesId) {
        this.notesId = notesId;
    }
}
