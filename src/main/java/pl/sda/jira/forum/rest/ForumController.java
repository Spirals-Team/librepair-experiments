package pl.sda.jira.forum.rest;

import pl.sda.jira.forum.domain.ForumRepository;

public class ForumController {
    private final ForumRepository forumRepository;

    public ForumController(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public boolean existsForSpace(String spaceId) {
        return forumRepository.existsFor(spaceId);
    }
}
