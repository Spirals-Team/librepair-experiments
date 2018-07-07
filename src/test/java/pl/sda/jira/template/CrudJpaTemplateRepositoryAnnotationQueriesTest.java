package pl.sda.jira.template;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CrudJpaTemplateRepositoryAnnotationQueriesTest {
    @Autowired private CrudJpaTemplateRepository repository;

    @Before
    public void init() {
        Template peterParker = new Template("peter", "parker");
        peterParker.setDescription(new Description("some spider"));
        repository.save(peterParker);

        Template maryJane = new Template("mary jane", "watson");
        maryJane.setDescription(new Description("strong woman"));
        repository.save(maryJane);

        Template peterDoe = new Template("peter", "doe");
        peterDoe.setDescription(new Description("some strong guy"));
        repository.save(peterDoe);

        Template peterR = new Template("peter", "rasputin");
        peterR.setDescription(new Description("some strong guy"));
        repository.save(peterR);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldFindTemplateDescription() {
        List<Description> result = repository.findDescriptionByName("peter", "whatever");

        assertEquals(3, result.size());
        assertEquals("name: peter, last name:doe", result.get(0).value());
    }
}