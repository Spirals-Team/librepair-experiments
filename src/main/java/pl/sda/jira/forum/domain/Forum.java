package pl.sda.jira.forum.domain;

public class Forum {
    private String forumId;
    private String name;

    public String getForumId() {
        return forumId;
    }

    public String getName() {
        return name;
    }

    Forum(String name, String forumId) {
        this.forumId = forumId;
        this.name = name;
    }

    public ForumDto asDto() {
        ForumDto forumDto = new ForumDto(forumId);
        forumDto.setName(name);
        return forumDto;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
