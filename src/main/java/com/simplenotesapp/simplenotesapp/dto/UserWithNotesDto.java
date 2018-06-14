package com.simplenotesapp.simplenotesapp.dto;

import java.util.Set;

public class UserWithNotesDto {

    private Long id;
    private String login;
    private String name;
    private String surname;
    private String password;
    private Set<NoteDto> notes;

    public UserWithNotesDto(final Long id, final String login, final String name, final String surname, final String password, final Set<NoteDto> notes) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.notes = notes;
    }

    public UserWithNotesDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<NoteDto> getNotes() {
        return notes;
    }

    public void setNotes(Set<NoteDto> notes) {
        this.notes = notes;
    }
}
