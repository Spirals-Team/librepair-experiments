
package idioms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DateHelper Class.
 */
public class DateHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DateHelper.class);
	private static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	/**
	 * Now.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar now() {
		final Calendar now = Calendar.getInstance();
		return now;
	}

	/**
	 * Yesterday.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar yesterday() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar;
	}

	/**
	 * Tomorrow.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar tomorrow() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, +1);
		return calendar;
	}

	/**
	 * Last week.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar lastWeek() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		return calendar;
	}

	/**
	 * Next week.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar nextWeek() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, +1);
		return calendar;
	}

	/**
	 * Last month.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar lastMonth() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return calendar;
	}

	/**
	 * Next month.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar nextMonth() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, +1);
		return calendar;
	}

	/**
	 * Last year.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar lastYear() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		return calendar;
	}

	/**
	 * Next year.
	 *
	 * calendar
	 *
	 * @return the calendar
	 */
	public static Calendar nextYear() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, +1);
		return calendar;
	}

	/**
	 *  Transform Calendar to ISO 8601 string.
	 *
	 * calendar
	 * string
	 *
	 * @param calendar the calendar
	 * @return the string
	 */
	public static String fromCalendar(final Calendar calendar) {
		final Date date = calendar.getTime();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_FORMAT);
		return simpleDateFormat.format(date);
	}

	/**
	 *  Transform ISO 8601 string to Calendar.
	 *
	 * iso 8601 string
	 * calendar
	 * parse exception
	 *
	 * @param iso8601string the iso 8601 string
	 * @return the calendar
	 * @throws ParseException the parse exception
	 */
	public static Calendar toCalendar(final String iso8601string) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_FORMAT);
		final Date date = simpleDateFormat.parse(iso8601string);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
}
