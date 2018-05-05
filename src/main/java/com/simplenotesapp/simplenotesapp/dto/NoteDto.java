package com.simplenotesapp.simplenotesapp.dto;

import java.util.Set;

public class NoteDto {

    private long id;
    private String title;
    private String content;
    private Set<Long> usersId;

    public NoteDto(final long id, final String title, final String content, final Set<Long> usersId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.usersId = usersId;
    }

    public NoteDto() {

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

    public Set<Long> getUsersId() {
        return usersId;
    }

    public void setUsersId(Set<Long> usersId) {
        this.usersId = usersId;
    }
}
