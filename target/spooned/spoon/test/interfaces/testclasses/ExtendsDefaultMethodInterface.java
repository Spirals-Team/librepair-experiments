package spoon.test.interfaces.testclasses;


public interface ExtendsDefaultMethodInterface extends spoon.test.interfaces.testclasses.InterfaceWithDefaultMethods {
    public default java.time.ZonedDateTime getZonedDateTime(java.lang.String zoneString) {
        try {
            return java.time.ZonedDateTime.of(getLocalDateTime(), java.time.ZoneId.of(zoneString));
        } catch (java.time.DateTimeException e) {
            java.lang.System.err.println((("Invalid zone ID: " + zoneString) + "; using the default time zone instead."));
            return java.time.ZonedDateTime.of(getLocalDateTime(), java.time.ZoneId.systemDefault());
        }
    }
}

