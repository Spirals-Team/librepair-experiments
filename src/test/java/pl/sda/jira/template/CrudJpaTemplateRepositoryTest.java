package pl.sda.jira.template;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CrudJpaTemplateRepositoryTest {
    private static final String SOME_NAME = "peter";
    private static final String SOME_LAST_NAME = "parker";
    private static final String SOME_DESCRIPTION = "some description";
    private static final String NEW_DESCRIPTION = "new description";
    @Autowired private CrudJpaTemplateRepository repository;

    @After
    public void removeAll()  {
//        repository.deleteAll();
    }

    @Test
    public void shouldAddTemplate() {
        Template template = new Template(SOME_NAME, SOME_LAST_NAME);

        Template saved = repository.save(template);

        assertEquals(SOME_NAME, saved.getName());
        assertNotNull(saved.getId());
    }

    @Test
    public void shouldGetTemplate() {
        Template saved = repository.save(new Template(SOME_NAME, SOME_LAST_NAME));

        Template result = repository.findOne(saved.getId());

        assertEquals(result, saved);
        assertNotSame(result, saved);
    }

    @Test
    public void shouldUpdateTemplate() {
        Template template = new Template(SOME_NAME, SOME_LAST_NAME);
        template.setDescription(new Description(SOME_DESCRIPTION));
        Template saved = repository.save(template);

        saved.setDescription(new Description(NEW_DESCRIPTION));
        repository.save(saved);
        Template result = repository.findOne(saved.getId());

        assertEquals(NEW_DESCRIPTION, result.getDescription());
    }

    @Test
    public void shouldRemoveTemplate() {
        Template saved = repository.save(new Template(SOME_NAME, SOME_LAST_NAME));

        repository.delete(saved.getId());

        assertNull(repository.findOne(saved.getId()));
    }
}