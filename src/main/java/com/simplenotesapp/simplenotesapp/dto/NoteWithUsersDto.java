package com.simplenotesapp.simplenotesapp.dto;

import java.util.Set;

public class NoteWithUsersDto {
    private long id;
    private String title;
    private String content;
    private Set<UserDto> users;

    public NoteWithUsersDto(final long id, final String title, final String content, final Set<UserDto> users) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.users = users;
    }

    public NoteWithUsersDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }
}
