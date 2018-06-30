package pl.sda.jira.calendar.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CalendarAlreadyExistsException extends RuntimeException {
    public CalendarAlreadyExistsException (String name) {
        super("Calendar: " + name + " already exists.");
    }
}
