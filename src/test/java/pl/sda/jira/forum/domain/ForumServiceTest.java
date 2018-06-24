package pl.sda.jira.forum.domain;

import org.junit.Test;
import pl.sda.jira.persistance.inmemory.InMemoryForumRepository;

import static org.junit.Assert.*;

public class ForumServiceTest {


    ForumRepository forumRepository = new InMemoryForumRepository();
    ForumService forumService = new ForumService(forumRepository);
    private final String forumId = "123";
    private final String name = "NAME";
    private final String name1 = "NAME1";

    @Test(expected = ForumDoesNotExistExcepton.class)
    public void shouldThrowExceptionWhenForumDoesNotExist() {
        ForumService forumService = new ForumService(new InMemoryForumRepository());

        forumService.get(forumId);

    }

    @Test
    public void shouldReturnForumWhenExist() {
        Forum forum = new Forum(forumId, name);
        forumRepository.add(forum);

        ForumDto forumDto = forumService.get(forumId);

        ForumDto expected = new ForumDto(name);
        expected.setName(name);

        assertEquals(expected, forumDto);
    }

    @Test
    public void shouldAddForumWhenDoesNotExist() {
        ForumDto forumDto = new ForumDto(name);

        String identifier = forumService.add(forumDto);

        ForumDto forumVerification = forumService.get(identifier);

        assertEquals(forumDto, forumVerification);

    }

    @Test
    public void shouldRemoveForumWhenExist() {
        ForumDto forumDto = new ForumDto(name);

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
        ForumDto forumDto = new ForumDto(name);

        String identifireForum = forumService.add(forumDto);

        forumService.update(identifireForum, new ForumDto(name1));

        ForumDto created = forumService.get(identifireForum);
        assertEquals(name1, created.getName());
    }
}
