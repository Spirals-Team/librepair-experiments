package pl.sda.jira.calendar.rest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.sda.jira.calendar.domain.Calendar;
import pl.sda.jira.calendar.domain.CalendarRepository;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/jira-sda-app.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CalendarControllerTest {
    @Autowired private CalendarRepository calendarRepository;
    @Autowired private CalendarController calendarController;

    @Test
    public void shouldNotFindCalendarForGivenPerson() {
        String personId = randomPersonId();

        boolean result = calendarController.existsForPersonWith(personId);

        Assert.assertFalse(result);
    }

    @Test
    public void shouldFindCalendarForGivenPerson() {
        String personId = randomPersonId();
        calendarRepository.add(new Calendar());

        boolean result = calendarController.existsForPersonWith(personId);

        Assert.assertTrue(result);
    }

    private String randomPersonId() {
        return UUID.randomUUID().toString();
    }
}