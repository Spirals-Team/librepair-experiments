package io.zero.epic;

import io.zero.epic.fn.Fn;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

/**
 * Period for datetime processing based on Java8
 */
class Period {

    private static final List<DateTimeFormatter> DATETIMES = new ArrayList<DateTimeFormatter>() {
        {
            this.add(Iso.DATE_TIME);
            this.add(Iso.INSTANT);
            this.add(Iso.RFC1123_DATE_TIME);
            this.add(Iso.COMMON);
            this.add(Iso.READBALE);
        }
    };

    private static final List<DateTimeFormatter> DATES = new ArrayList<DateTimeFormatter>() {
        {
            this.add(Iso.DATE);
            this.add(Iso.BASIC_DATE);
            this.add(Iso.ORDINAL_DATE);
        }
    };

    private static final List<DateTimeFormatter> TIMES = new ArrayList<DateTimeFormatter>() {
        {
            this.add(Iso.TIME);
        }
    };

    private Period() {
    }

    /**
     * Convert to datetime
     *
     * @param literal
     * @return
     */
    static LocalDateTime toDateTime(final String literal) {
        final Optional<DateTimeFormatter> hit =
                Fn.getNull(Optional.empty(),
                        () -> DATETIMES.stream()
                                .filter(formatter ->
                                        null != Fn.getJvm(
                                                null,
                                                () -> LocalDateTime.parse(literal, formatter),
                                                literal))
                                .findAny(), literal);
        return hit.isPresent() ? LocalDateTime.parse(literal, hit.get()) : null;
    }

    /**
     * Convert to date
     *
     * @param date
     * @return
     */
    static LocalDateTime toDateTime(final Date date) {
        return toDateTime(date.toInstant());
    }


    static LocalDateTime toDateTime(final Instant instant) {
        final ZoneId zone = ZoneId.systemDefault();
        final ZoneOffset offset = zone.getRules().getOffset(Instant.now());
        return LocalDateTime.ofInstant(instant, offset);
    }

    /**
     * Convert to date
     *
     * @param literal
     * @return
     */
    static LocalDate toDate(final String literal) {
        final Optional<DateTimeFormatter> hit =
                Fn.getNull(Optional.empty(),
                        () -> DATES.stream()
                                .filter(formatter ->
                                        null != Fn.getJvm(
                                                null,
                                                () -> LocalDate.parse(literal, formatter),
                                                literal))
                                .findFirst(), literal);
        return hit.isPresent() ? LocalDate.parse(literal, hit.get()) : null;
    }

    /**
     * Convert to date
     *
     * @param date
     * @return
     */
    static LocalDate toDate(final Date date) {
        final LocalDateTime datetime = toDateTime(date);
        return datetime.toLocalDate();
    }

    static LocalDate toDate(final Instant instant) {
        final LocalDateTime datetime = toDateTime(instant);
        return datetime.toLocalDate();
    }

    /**
     * Convert to time
     *
     * @param literal
     * @return
     */
    static LocalTime toTime(final String literal) {
        final Optional<DateTimeFormatter> hit =
                Fn.getNull(Optional.empty(),
                        () -> TIMES.stream()
                                .filter(formatter ->
                                        null != Fn.getJvm(
                                                null,
                                                () -> LocalTime.parse(literal, formatter),
                                                literal))
                                .findFirst(), literal);
        return hit.isPresent() ? LocalTime.parse(literal, hit.get()) : null;
    }

    /**
     * Convert to date
     *
     * @param date
     * @return
     */
    static LocalTime toTime(final Date date) {
        final LocalDateTime datetime = toDateTime(date);
        return datetime.toLocalTime();
    }

    static LocalTime toTime(final Instant instant) {
        final LocalDateTime datetime = toDateTime(instant);
        return datetime.toLocalTime();
    }

    /**
     * Check whether it's valid
     *
     * @param literal
     * @return
     */
    static boolean isValid(final String literal) {
        final Date parsed = parse(literal);
        return null != parsed;
    }

    private static DateTimeFormatter analyzeFormatter(final String pattern, final String literal) {
        final DateTimeFormatter formatter;
        if (19 == pattern.length()) {
            // 2018-07-29T16:26:49格式的特殊处理
            formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        } else if (23 == pattern.length()) {
            formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        } else if (literal.contains("\\+") || literal.contains("\\-")) {
            formatter = DateTimeFormatter.ofPattern(Storage.ADJUST_TIME, Locale.US);
        } else {
            formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        }
        return formatter;
    }

    static Date parse(final String literal) {

        return Fn.getNull(null, () -> {
            String target = literal;
            if (target.contains("T")) {
                target = target.replace('T', ' ');
            }
            final int length = target.length();
            final String pattern = Storage.PATTERNS_MAP.get(length);
            if (null != pattern) {
                final DateTimeFormatter formatter = analyzeFormatter(pattern, literal);
                final Date converted;
                if (10 == pattern.length()) {
                    final LocalDate date = LocalDate.parse(target, formatter);
                    final ZoneId zoneId = getAdjust(literal);
                    converted = parse(date, zoneId);
                } else if (15 > pattern.length()) {
                    final LocalTime time = LocalTime.parse(target, formatter);
                    final ZoneId zoneId = getAdjust(literal);
                    converted = parse(time, zoneId);
                } else {
                    final LocalDateTime datetime = LocalDateTime.parse(target, formatter);
                    final ZoneId zoneId = getAdjust(literal);
                    converted = parse(datetime, zoneId);
                }
                return converted;
            } else {
                return parseFull(literal);
            }
        }, literal);
    }

    private static ZoneId getAdjust(final String literal) {
        if (literal.endsWith("Z")) {
            return ZoneId.from(ZoneOffset.UTC);
        } else {
            return ZoneId.systemDefault();
        }
    }

    /**
     * Not recommend directly for deep parsing
     *
     * @param literal
     * @return
     */
    static Date parseFull(final String literal) {
        return Fn.getNull(null, () -> {
            // Datetime parsing
            final LocalDateTime datetime = toDateTime(literal);
            final ZoneId zoneId = getAdjust(literal);
            return Fn.nullFlow(datetime,
                    (ref) -> Date.from(ref.atZone(zoneId).toInstant()),
                    () -> {
                        // Date parsing
                        final LocalDate date = toDate(literal);
                        return Fn.nullFlow(date,
                                (ref) -> Date.from(ref.atStartOfDay().atZone(zoneId).toInstant()),
                                () -> {
                                    // Time parsing
                                    final LocalTime time = toTime(literal);
                                    return null == time ? null : parse(time);
                                });
                    });
        }, literal);
    }

    static List<String> valueDurationDays(final String from, final String to) {
        final List<String> result = new ArrayList<String>();
        LocalDate begin = LocalDate.parse(from);
        result.add(begin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)));
        final LocalDate end = LocalDate.parse(to);
        while (end.isAfter(begin)) {
            begin = begin.plusDays(1);
            result.add(begin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)));
        }
        return result;
    }


    static void itDay(final String from, final String to,
                      final Consumer<Date> consumer) {
        LocalDateTime begin = toDateTime(parseFull(from));
        final LocalDateTime end = toDateTime(parseFull(to));
        do {
            consumer.accept(parse(begin));
            begin = begin.plusDays(1);
        } while (end.isAfter(begin));
    }

    static void itWeek(final String from, final String to,
                       final Consumer<Date> consumer) {
        LocalDate begin = toDate(parseFull(from));
        final LocalDate end = toDate(parseFull(to));
        do {
            consumer.accept(parse(begin));
            begin = begin.plusWeeks(1);
        } while (end.isAfter(begin));
    }

    static boolean equalDate(final Date left, final Date right) {
        // Compare year
        int leftVal = toItem(left, Calendar.YEAR);
        int rightVal = toItem(right, Calendar.YEAR);
        if (leftVal == rightVal) {
            // Compare month
            leftVal = toItem(left, Calendar.MONTH);
            rightVal = toItem(right, Calendar.MONTH);
            if (leftVal == rightVal) {
                // Compare day
                leftVal = toItem(left, Calendar.DAY_OF_MONTH);
                rightVal = toItem(right, Calendar.DAY_OF_MONTH);
                return leftVal == rightVal;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    static int toMonth(final String literal) {
        final Date date = parse(literal);
        return toItem(date, Calendar.MONTH);
    }

    static int toMonth(final Date date) {
        return toItem(date, Calendar.MONTH);
    }

    static int toYear(final Date date) {
        return toItem(date, Calendar.YEAR);
    }

    static int toYear(final String literal) {
        final Date date = parse(literal);
        return toItem(date, Calendar.YEAR);
    }

    private static int toItem(final Date date, final int flag) {
        final Calendar issue = Calendar.getInstance();
        issue.setTime(date);
        return issue.get(flag);
    }

    static Date parse(final LocalTime time) {
        return parse(time, ZoneId.systemDefault());
    }

    static Date parse(final LocalTime time, final ZoneId zoneId) {
        final LocalDate date = LocalDate.now();
        final LocalDateTime datetime = LocalDateTime.of(date, time);
        return Date.from(datetime.atZone(zoneId).toInstant());
    }

    static Date parse(final LocalDateTime datetime) {
        return parse(datetime, ZoneId.systemDefault());
    }

    static Date parse(final LocalDateTime datetime, final ZoneId zoneId) {
        return Date.from(datetime.atZone(zoneId).toInstant());
    }

    static Date parse(final LocalDate datetime) {
        return parse(datetime, ZoneId.systemDefault());
    }

    static Date parse(final LocalDate datetime, final ZoneId zoneId) {
        return Date.from(datetime.atStartOfDay(zoneId).toInstant());
    }
}
