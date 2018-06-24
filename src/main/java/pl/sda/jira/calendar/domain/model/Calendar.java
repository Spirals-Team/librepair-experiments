package pl.sda.jira.calendar.domain.model;

public class Calendar {
    private final String ID;
    private String name;

    public Calendar(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public boolean hasSameNameAs(String name) {
        return this.name.equals(name);
    }

    public void changeCalendarName(String name) {
        this.name = name;
    }
}
