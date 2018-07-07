package pl.sda.jira.calendar.domain.exception;

public class CalendarNotFoundException extends RuntimeException {
    public CalendarNotFoundException (String id) {
        super("Calendar id: " + id + " does not exist.");
    }
}
