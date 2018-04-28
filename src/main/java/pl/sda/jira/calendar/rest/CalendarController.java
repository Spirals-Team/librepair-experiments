package pl.sda.jira.calendar.rest;

import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.jira.calendar.domain.CalendarRepository;

public class CalendarController {
    private CalendarRepository calendarRepository;

    @Autowired
    public CalendarController(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public boolean existsForPersonWith(String personId) {
        return calendarRepository.existsForPersonWith(personId);
    }
}
