package pl.sda.jira.persistance.inmemory;

import org.springframework.stereotype.Repository;
import pl.sda.jira.forum.domain.Forum;
import pl.sda.jira.forum.domain.ForumRepository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryForumRepository implements ForumRepository {

    private final Map<String, Forum> forumMap = new HashMap<>();

    @Override
    public Forum get(String forumId) {
        return forumMap.get(forumId);
    }

    @Override
    public boolean exists(String forumId) {
        return forumMap.containsKey(forumId);
    }

    @Override
    public void add(Forum forum) {
        forumMap.put(forum.getForumId(), forum);
    }

    @Override
    public void replace(Forum forum) {
        forumMap.replace(forum.getForumId(), forum);
    }

    @Override
    public void remove(String forumId) {
        forumMap.remove(forumId);
    }

}