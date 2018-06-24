package pl.sda.jira.forum.domain;

public class ForumIdentifier {
    private long nextId = 1L;

    public long create() {
        return nextId++;
    }
}
