package ru.iac.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TimeFormat {
    public static String getTime(Timestamp timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()),
                TimeZone.getDefault().toZoneId());
        return localDateTime.format(DateTimeFormatter.ofPattern("d.MM.uuuu HH:mm"));
    }
}
