package pl.sda.jira.forum.domain;

import org.junit.Test;
import pl.sda.jira.persistance.inmemory.InMemoryForumRepository;

import static org.junit.Assert.*;

public class ForumServiceTest {


    private ForumRepository forumRepository = new InMemoryForumRepository();
    private ForumService forumService = new ForumService(forumRepository);
    private final String FORUMID = "123";
    private final String NAME = "NAME";
    private final String NAME1 = "NAME1";

    @Test(expected = ForumDoesNotExistExcepton.class)
    public void shouldThrowExceptionWhenForumDoesNotExist() {
        ForumService forumService = new ForumService(new InMemoryForumRepository());

        forumService.get(FORUMID);

    }

    @Test
    public void shouldReturnForumWhenExist() {
        Forum forum = new Forum(NAME, FORUMID);
        forumRepository.add(forum);

        ForumDto forumDto = forumService.get(FORUMID);

        ForumDto expected = new ForumDto(NAME);
        expected.setName(NAME);

        assertEquals(expected, forumDto);
    }

    @Test
    public void shouldAddForumWhenDoesNotExist() {
        ForumDto forumDto = new ForumDto(NAME);

        String identifier = forumService.add(forumDto);

        ForumDto forumVerification = forumService.get(identifier);

        assertEquals(forumDto, forumVerification);

    }

    @Test
    public void shouldRemoveForumWhenExist() {
        ForumDto forumDto = new ForumDto(NAME);

        String identifireForum = forumService.add(forumDto);

        forumService.remove(identifireForum);

        String forumExist = assertForumDoesNotExist(identifireForum);

        assertEquals("Forum should be not found", forumExist);
    }

    private String assertForumDoesNotExist(String identifireForum) {
        if (forumService.exist(identifireForum)) {
            return "Forum exists";
        } else {
            return "Forum should be not found";
        }
    }

    @Test
    public void shouldUpdateForum(){
        ForumDto forumDto = new ForumDto(NAME);

        String identifireForum = forumService.add(forumDto);

        forumService.update(identifireForum, new ForumDto(NAME1));

        ForumDto created = forumService.get(identifireForum);
        assertEquals(NAME1, created.getName());
    }
}
