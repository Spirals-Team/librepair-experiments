package pl.sda.jira.forum.domain;

public class FakeForumRepository implements ForumRepository {
    private boolean somethingAdded = false;

    public void add(Forum forum) {
        somethingAdded = true;
    }

    public boolean existsFor(String spaceId) {
        return somethingAdded;
    }
}
