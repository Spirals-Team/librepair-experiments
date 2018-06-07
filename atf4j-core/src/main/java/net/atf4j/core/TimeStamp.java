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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeStamp class.
 */
public final class TimeStamp {

    /** ISO_DATE_TIME constant. */
    public static final String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm'Z'";

    /** DATE_TIME_FORMAT constant. */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm.ss";

    /**
     * private constructor to prevent wild instantiation.
     */
    private TimeStamp() {
        super();
    }

    /**
     * Date time.
     *
     * @return the string
     */
    public static String getDateTime() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }

    /**
     * Date time.
     *
     * @param format the format
     * @return the string
     */
    public static String getDateTime(final String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

}
