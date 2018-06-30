package pl.sda.jira.calendar.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CalendarNotFoundException extends RuntimeException {
    public CalendarNotFoundException (String id) {
        super("Calendar id: " + id + " does not exist.");
    }
}
