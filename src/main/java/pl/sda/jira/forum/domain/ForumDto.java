package pl.sda.jira.forum.domain;

import java.util.Objects;

public class ForumDto {

    private String name;

    public ForumDto(String name) {
        this.name = name;
    }

    public ForumDto(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForumDto)) return false;
        ForumDto forumDto = (ForumDto) o;
        return name == forumDto.name;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
