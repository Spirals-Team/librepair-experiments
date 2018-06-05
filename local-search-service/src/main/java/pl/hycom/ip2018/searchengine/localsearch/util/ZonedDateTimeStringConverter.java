package pl.hycom.ip2018.searchengine.localsearch.util;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Util class to make conversions between many formats of date
 */
public class ZonedDateTimeStringConverter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private String toStringFromZonedDateTime(ZonedDateTime value) {
        return value.truncatedTo(ChronoUnit.SECONDS).format(formatter);
    }

    private ZonedDateTime toZonedDateTimeFromLong(Long value) {
        Instant instant = Instant.ofEpochMilli(value);
        return ZonedDateTime.ofInstant(instant, ZonedDateTime.now().getZone());
    }

    public String toStringFromLong(Long value) {
        return toStringFromZonedDateTime(toZonedDateTimeFromLong(value));
    }
}
