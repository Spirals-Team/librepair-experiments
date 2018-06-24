package pl.sda.jira.calendar.domain.dto;

public class CalendarDto {
    public final String NAME;

    public CalendarDto(String name) {
        NAME = name;
    }

    public static class Builder {
        private final String NAME;

        private Builder(String name) {
            NAME = name;
        }

        public static Builder aCalendar(String name) {
            return new Builder(name);
        }

        public CalendarDto build() {
            return new CalendarDto(NAME);
        }
    }
}
