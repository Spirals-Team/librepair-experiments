package pl.sda.jira.calendar.domain.dto;


public class CalendarDto {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CalendarDto() {}

    public CalendarDto(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
    }

    public static class Builder {
        private final String name;
        private String id;


        private Builder(String name) {
            this.name = name;
        }


        public static Builder aCalendar(String name) {
            return new Builder(name);
        }

        public CalendarDto build() {
            return new CalendarDto(this);
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }
    }
}
