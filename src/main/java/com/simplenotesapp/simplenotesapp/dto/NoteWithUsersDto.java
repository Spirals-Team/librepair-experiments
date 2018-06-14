package com.simplenotesapp.simplenotesapp.dto;

import java.time.ZonedDateTime;
import java.util.Set;

public class NoteWithUsersDto {

    private long id;
    private String title;
    private String content;
    private ZonedDateTime createdTime;
    private ZonedDateTime modifiedTime;
    private Set<UserDto> users;

    public NoteWithUsersDto(final long id, final String title, final String content, final ZonedDateTime createdTime,
                            final ZonedDateTime modifiedTime, final Set<UserDto> users) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
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

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ZonedDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(ZonedDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }
}
