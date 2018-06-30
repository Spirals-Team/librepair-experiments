package pl.sda.jira.calendar.rest.domain.exception;

import org.junit.Test;
import pl.sda.jira.calendar.rest.exception.CalendarAlreadyExistsException;

import static org.junit.Assert.assertEquals;

public class CalendarAlreadyExistsExceptionTest {
    private static final String NAME = "calendar1";
    @Test
    public void shouldShowAppropriateMessage(){
        
        CalendarAlreadyExistsException exception = new CalendarAlreadyExistsException(NAME);
        assertEquals("Calendar: " + NAME + " already exists.", exception.getMessage());
    }
}
