package pl.sda.jira.calendar.persistency.inmemory;

import pl.sda.jira.calendar.domain.Calendar;
import pl.sda.jira.calendar.domain.CalendarRepository;

public class InMemoryCalendarRepository implements CalendarRepository {
    private boolean added = false;

    public void add(Calendar calendar) {
        added = true;
    }

    public boolean existsForPersonWith(String personId) {
        return added;
    }
}
