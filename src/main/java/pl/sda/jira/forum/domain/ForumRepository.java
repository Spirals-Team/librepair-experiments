package pl.sda.jira.forum.domain;

public interface ForumRepository {
    void add(Forum forum);

    boolean existsFor(String spaceId);
}
