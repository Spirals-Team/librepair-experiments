package pl.sda.jira.calendar.domain;

public interface CalendarRepository {
    void add(Calendar calendar);

    boolean existsForPersonWith(String personId);
}
