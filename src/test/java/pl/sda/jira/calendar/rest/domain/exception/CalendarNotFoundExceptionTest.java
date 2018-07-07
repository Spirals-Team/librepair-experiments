package pl.sda.jira.calendar.rest.domain.exception;

import org.junit.Test;
import pl.sda.jira.calendar.domain.exception.CalendarNotFoundException;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CalendarNotFoundExceptionTest {

    private static final String SOME_ID = UUID.randomUUID().toString();

    @Test
    public void shouldShowAppropriateMessage(){
        CalendarNotFoundException exception = new CalendarNotFoundException(SOME_ID);
        assertEquals("Calendar id: " + SOME_ID + " does not exist.", exception.getMessage());
    }
}
