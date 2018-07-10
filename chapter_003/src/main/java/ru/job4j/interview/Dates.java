package ru.job4j.interview;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Utility class to work/transform with date.
 */
public final class Dates {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("d MMM yy, HH:mm", new Locale("ru", "RU"));
    public static final DateTimeFormatter TIME_FORMATTER =
        DateTimeFormatter.ofPattern("HH:mm", new Locale("ru", "RU"));

    /**
     * Parse date and time from string
     *
     * @param dateText date text
     * @param format   date formatter
     * @return local date time
     */
    public LocalDateTime parseDateTime(final String dateText,
                                       final DateTimeFormatter format) {
        return LocalDateTime.parse(dateText, format);
    }

    /**
     * Parse date from string
     *
     * @param dateText date text
     * @param format   date formatter
     * @return local date
     */
    public LocalDate parseDate(final String dateText,
                               final DateTimeFormatter format) {
        return LocalDate.parse(dateText, format);
    }

    /**
     * Parse time from string
     *
     * @param dateText date text
     * @param format   date formatter
     * @return local time
     */
    public LocalTime parseTime(final String dateText,
                               final DateTimeFormatter format) {
        return LocalTime.parse(dateText, format);
    }
}
