package pl.sda.jira.calendar.domain.model;

import pl.sda.jira.calendar.domain.dto.CalendarDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Calendar {
    @Id
    @GeneratedValue private String id;
    private String name;

    public Calendar(String id, String name) {
        this.id = id;
        this.name = name;
    }

     private Calendar() {}

    public Calendar(CalendarDto calendarDto) {
        this.name = calendarDto.getName();
    }


    public String getId() {
        return id;
    }

    public boolean hasSameNameAs(String name) {
        return this.name.equals(name);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public CalendarDto asDto() {
        return CalendarDto.Builder.aCalendar(name).withId(id).build();
    }
}
