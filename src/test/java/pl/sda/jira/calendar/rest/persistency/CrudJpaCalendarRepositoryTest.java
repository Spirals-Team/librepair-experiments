package pl.sda.jira.calendar.rest.persistency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sda.jira.calendar.domain.CrudJpaCalendarRepository;
import pl.sda.jira.calendar.domain.model.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class CrudJpaCalendarRepositoryTest {
    @Autowired private CrudJpaCalendarRepository repository;

    private String id = "3423423";
    private String name = "calendar1";
    private Calendar calendar = new Calendar(id, name);

    @Test
    public void shouldAddCalendar() {

        Calendar saved = repository.save(calendar);

        assertEquals(name, saved.asDto().getName());
        assertNotNull(saved.getId());
    }

    @Test
    public void shouldRemoveCalendar() {

        Calendar saved = repository.save(calendar);
        repository.delete(saved.getId());
        assertFalse(repository.exists(saved.getId()));
    }

    @Test
    public void shouldUpdateCalendar() {
        String newName = "calendarium";
        Calendar saved = repository.save(calendar);
        saved.changeName(newName);
        assertEquals(newName, saved.asDto().getName());
    }

    @Test
    public void shouldGetCalendar(){
        Calendar saved = repository.save(calendar);
        Calendar sameCalendar = repository.findOne(saved.getId());
        assertEquals(saved.toString(), sameCalendar.toString());

    }

}
