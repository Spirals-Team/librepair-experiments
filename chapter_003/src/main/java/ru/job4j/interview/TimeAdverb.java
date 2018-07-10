package ru.job4j.interview;

import static java.lang.String.format;

/**
 * Enum represent a time adverb.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public enum TimeAdverb {

    YESTERDAY("вчера"),
    TODAY("сегодня");

    private String value;

    TimeAdverb(final String value) {
        this.value = value;
    }

    /**
     * Convert string to enum by string text
     *
     * @param text string
     * @return enum
     */
    public static TimeAdverb timeAdverbFromTitle(final String text) {
        for (TimeAdverb timeAdverb : TimeAdverb.values()) {
            if (timeAdverb.value.equalsIgnoreCase(text)) {
                return timeAdverb;
            }
        }
        throw new IllegalArgumentException(
            format("TimeAdverb with text: {%s}, not found", text));
    }


    @Override
    public String toString() {
        return String.format("TimeAdverb{value='%s'}", value);
    }

    public String getValue() {
        return value;
    }
}