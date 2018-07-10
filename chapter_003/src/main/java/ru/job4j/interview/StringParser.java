package ru.job4j.interview;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to parse strings
 */
public final class StringParser {

    private static final Pattern VACANCY_ID_PATTERN =
        Pattern.compile("^http:\\/\\/www\\.sql\\.ru\\/forum\\/(\\d+)\\/.+$");
    private static final Pattern TIME_ADVERBS_PATTERN =
        Pattern.compile("^[\\S|\\s]+\\s(\\d{1,2}:\\d{1,2})$");

    /**
     * Parse vacancy id from link
     *
     * @param rawText rawText
     * @return id
     */
    public String getVacancyID(final String rawText) {
        return getMatcherGroup(rawText, VACANCY_ID_PATTERN);
    }

    /**
     * Parse time hh:mm from string
     *
     * @param rawText rawText
     * @return time
     */
    public String getTimeText(final String rawText) {
        return getMatcherGroup(rawText, TIME_ADVERBS_PATTERN);
    }

    private String getMatcherGroup(final String rawText,
                                   final Pattern pattern) {
        Matcher matcher = pattern.matcher(rawText);
        String result = "";
        if (matcher.matches()) {
            result = matcher.group(1).trim();
        }
        return result;
    }
}
