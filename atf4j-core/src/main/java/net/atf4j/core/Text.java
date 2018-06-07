/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.core;

import java.util.Random;

/**
 * Text utilities class.
 */
public final class Text extends TestResultsReporting {

    /** The Constant RND. */
    private static final Random RND = new Random(System.currentTimeMillis());

    /**
     * Instantiates a new text.
     */
    private Text() {
        super();
    }

    /**
     * Random char.
     *
     * @return the char
     */
    public static char randomChar() {
        return (char) ('a' + RND.nextInt(26));
    }

    /**
     * Random string.
     *
     * @param length the length
     * @return the string
     */
    public static String randomString(final int length) {
        final StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(randomChar());
        }
        return stringBuffer.toString();
    }

    /**
     * Random digit.
     *
     * @return the char
     */
    public static char randomDigit() {
        return (char) ('0' + RND.nextInt(9));
    }

    /**
     * Random digits.
     *
     * @param length the length
     * @return the string
     */
    public static String randomDigits(final int length) {
        final StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(randomDigit());
        }
        return stringBuffer.toString();
    }

    /**
     * Capitalise as Name.
     *
     * @param input as String.
     * @return string input as capitalised String.
     */
    public static String capitalise(final String input) {
        if (input != null) {
            if (input.length() > 0) {
                final String trim = input.trim();
                final String lowerCase = trim.toLowerCase();
                final StringBuffer buffer = new StringBuffer(lowerCase);
                buffer.setCharAt(0, Character.toUpperCase(input.charAt(0)));
                return buffer.toString();
            }
        }
        return "";
    }

    /**
     * Fill.
     *
     * @param length the length
     * @return the string
     */
    public static String fill(final int length) {
        return padToLength("", length);
    }

    /**
     * Fill.
     *
     * @param length the length
     * @param chr the chr
     * @return the string
     */
    public static String fill(final int length, final char chr) {
        return padToLength("", length, chr);
    }

    /**
     * Pad to length.
     *
     * @param stem the stem
     * @param length the length
     * @return the string
     */
    public static String padToLength(final String stem, final int length) {
        return padToLength(stem, length, ' ');
    }

    /**
     * Pad to length.
     *
     * @param stem the stem
     * @param length the length
     * @param chr the chr
     * @return the string
     */
    public static String padToLength(final String stem, final int length, final char chr) {
        final StringBuilder builder = new StringBuilder(stem);
        while (builder.length() < length) {
            builder.append(chr);
        }
        return builder.toString();
    }

}
