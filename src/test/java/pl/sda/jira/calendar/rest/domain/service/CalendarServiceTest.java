package pl.sda.jira.calendar.rest.domain.service;

import org.junit.Test;
import pl.sda.jira.calendar.domain.dto.CalendarDto;
import pl.sda.jira.calendar.domain.model.Calendar;
import pl.sda.jira.calendar.domain.CalendarRepository;
import pl.sda.jira.calendar.domain.service.CalendarId;
import pl.sda.jira.calendar.domain.service.CalendarService;
import pl.sda.jira.calendar.domain.exception.CalendarNotFoundException;
import pl.sda.jira.calendar.persistency.inmemory.InMemoryCalendarRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static pl.sda.jira.calendar.domain.dto.CalendarDto.Builder.aCalendar;

public class CalendarServiceTest {

    private static final String CALENDAR_NAME = "Calendar 1";
    private static final String NEW_NAME = "Calendar 1234";
    private CalendarRepository calendarRepository = new InMemoryCalendarRepository();
    private CalendarService calendarService = new CalendarService(calendarRepository, new CalendarId());

    @Test (expected = CalendarNotFoundException.class)
    public void shouldThrowExceptionWhenCalendarDoesNotExist(){
        calendarService.findBy(randomId());

    }

    @Test
    public void shouldFindCalendar(){
        String id = randomId();
        Calendar calendar = new Calendar(id, CALENDAR_NAME);
        calendarRepository.add(calendar);
        Calendar result = calendarService.findBy(id);
        assertEquals(calendar, result);
    }

    @Test
    public void shouldAddCalendar() {
        CalendarDto calendarDto = createCalendarDto();

        String calendarId = calendarService.add(calendarDto);
        Calendar created = calendarService.findBy(calendarId);
        assertTrue(created.hasSameNameAs(CALENDAR_NAME));
    }

    @Test
    public void shouldRemoveCalendar() {
        String id = calendarService.add(createCalendarDto());

        calendarService.remove(id);

        assertCalendarDoesNotExist(id);
    }

    private void assertCalendarDoesNotExist(String id) {
        try {
            calendarService.findBy(id);
            fail("Calendar should not be found.");
        } catch (CalendarNotFoundException exception) {
            assertEquals("Calendar id: " + id + " does not exist.", exception.getMessage());
        }
    }

    @Test
    public void shouldUpdateCalendar() {
        String id =calendarService.add(createCalendarDto());

        calendarService.update(id, aCalendarDtoWith(NEW_NAME));

        Calendar created = calendarService.findBy(id);
        assertTrue(created.hasSameNameAs(NEW_NAME));
    }

    private CalendarDto createCalendarDto() {
        return aCalendarDtoWith(CALENDAR_NAME);
    }

    private CalendarDto aCalendarDtoWith(String name) {
        return aCalendar(name).build();
    }


    private String randomId() {
       return UUID.randomUUID().toString();
    }

}
