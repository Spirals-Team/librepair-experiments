package pl.sda.jira.forum.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sda.jira.forum.domain.Forum;
import pl.sda.jira.forum.domain.ForumRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/jira-sda-app.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ForumControllerTest {
    private static final String SOME_SPACE_ID = "13";

    @Autowired private ForumRepository forumRepository;
    @Autowired private ForumController forumController;

    @Test
    public void forumShouldNotExistForGivenSpace() {
        boolean exists = forumController.existsForSpace(SOME_SPACE_ID);

        assertFalse(exists);
    }

    @Test
    public void forumShouldExistForGivenSpace() {
        forumRepository.add(new Forum());

        boolean exists = forumController.existsForSpace(SOME_SPACE_ID);

        assertTrue(exists);
    }
}