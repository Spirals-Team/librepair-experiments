package spoon.test.interfaces.testclasses;


public interface InterfaceWithDefaultMethods {
    void setTime(int hour, int minute, int second);

    void setDate(int day, int month, int year);

    void setDateAndTime(int day, int month, int year, int hour, int minute, int second);

    java.time.LocalDateTime getLocalDateTime();

    static java.time.ZoneId getZoneId(java.lang.String zoneString) {
        try {
            return java.time.ZoneId.of(zoneString);
        } catch (java.time.DateTimeException e) {
            java.lang.System.err.println((("Invalid time zone: " + zoneString) + "; using default time zone instead."));
            return java.time.ZoneId.systemDefault();
        }
    }

    default java.time.ZonedDateTime getZonedDateTime(java.lang.String zoneString) {
        return java.time.ZonedDateTime.of(getLocalDateTime(), spoon.test.interfaces.testclasses.InterfaceWithDefaultMethods.getZoneId(zoneString));
    }
}

