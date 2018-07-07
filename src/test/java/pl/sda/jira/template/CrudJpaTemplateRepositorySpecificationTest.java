package pl.sda.jira.template;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static pl.sda.jira.template.TemplateSpecifications.byDescription;
import static pl.sda.jira.template.TemplateSpecifications.byName;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CrudJpaTemplateRepositorySpecificationTest {
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
    public void shouldFindByName() {
        Template template = repository.findOne(new ByNameTemplateSpecification("mary jane"));

        assertEquals("strong woman", template.getDescription());
    }

    @Test
    public void shouldFindByDescription() {
        Template template = repository.findOne(new ByDescriptionTemplateSpecification("strong woman"));

        assertEquals("mary jane", template.getName());
    }

    @Test
    public void shouldFindByDescriptionOrName() {
        Specification<Template> specifications = Specifications
                .where(new ByNameTemplateSpecification("mary jane"))
                .or(byDescription("some spider"));

        List<Template> template = repository.findAll(specifications);

        assertEquals(2, template.size());
    }

    @Test
    public void shouldFindByDescriptionAndName() {
        Specification<Template> specifications = Specifications
                .where(byName("mary jane"))
                .and(byDescription("strong woman"));

        Template template = repository.findOne(specifications);

        assertEquals("mary jane", template.getName());
    }

    @Test
    public void shouldCountDifferentName() {
        Specification<Template> specifications = Specifications
                .not(new ByNameTemplateSpecification("mary jane"));

        long template = repository.count(specifications);
        // select count(*) from Template where

        assertEquals(3, template);
    }

    @Test
    public void shouldFindAllWithDifferentName() {
        Specification<Template> specifications = Specifications
                .not(new ByNameTemplateSpecification("mary jane"));

        // select * from Template where
        List<Template> template = repository.findAll(specifications);

        assertEquals(3, template.size());
    }
}