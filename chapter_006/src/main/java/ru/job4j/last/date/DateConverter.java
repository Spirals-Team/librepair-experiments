package ru.job4j.last.date;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A class for working with date and converting it from string
 * format to a long number
 * @author Yury Matskevich
 */
public class DateConverter implements IDateConverter {
	private static final Logger LOG = Logger.getLogger(DateConverter.class);
	private Map<String, String> months = new HashMap<>();

	/**
	 * Creates a new object of {@link DateConverter} which allows some
	 * method for converting a date from string to long format
	 */
	public DateConverter() {
		fillMonthMap();
	}

	@Override
	public long getLongOfStartOfCurrentYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return getLongFromDayMonthYear("01jan" + year);
	}

	@Override
	public long convertInLong(String str) {
		long result = 0;
		if (str.matches("\\сегодня,\\s\\d+:\\d+")) {
			result = getLongFromDayMonthYear(createDateFromString(today()));
		}
		if (str.matches("\\вчера,\\s\\d+:\\d+")) {
			result = getLongFromDayMonthYear(createDateFromString(today())) - (24 * 60 * 60 * 1000);
		}
		if (str.matches("\\d+\\s[А-я]{3}\\s\\d{2},\\s\\d+:\\d+")) {
			result = getLongFromDayMonthYear(createDateFromString(str));
		}
		result += convertHoursAndMinutesToLong(str.split(",\\s")[1].split(":"));
		return result;
	}

	/**
	 * Gets a long number from a date.
	 * @param date a date in formt of ddMMMyy
	 * @return the representation of date in the form of a
	 * long number
	 */
	private long getLongFromDayMonthYear(String date) {
		SimpleDateFormat f = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
		long milliseconds = 0;
		try {
			java.util.Date d = f.parse(date);
			milliseconds = d.getTime();
		} catch (ParseException e) {
			LOG.error(e.getMessage(), e);
		}
		return milliseconds;
	}

	/**
	 * Gets today's date in format of String
	 * @return the representation of current date in
	 * form of dd MMM yy, HH:mm
	 */
	private String today() {
		return LocalDateTime
				.now(ZoneId.of("Europe/Moscow"))
				.format(DateTimeFormatter.ofPattern("dd MMM yy")) + ", 00:00";
	}

	/**
	 * Converts a date which has a format like dd MM yy, HH:mm
	 * in Russian to ddMMyy in English
	 * @param date a date in format dd MM yy, HH:mm, where MM -
	 * first three letter of month's name in Russian
	 * @return a string which represents a date in format ddMMMyy,
	 * where MMM - a full month's name in English
	 */
	private String createDateFromString(String date) {
		String[] array = date.split(",\\s")[0].split("\\s");
		return array[0] + convertFromRusToEng(array[1]) + array[2];
	}

	/**
	 * Gets a long number from diven hours and minutes
	 * @param time an array of {@link String}[] which consists
	 * hours in time[0] and minutes in time[1]
	 * @return the representation of time in the form of a
	 * long number
	 */
	private long convertHoursAndMinutesToLong(String[] time) {
		byte hours = (byte) Integer.parseInt(time[0]);
		byte minutes = (byte) Integer.parseInt(time[1]);
		return ((hours * 60 + minutes) * 60) * 1000;
	}

	/**
	 * Converts name of month from Russian to England
	 * @param month first three letters of month in Russian
	 * @return a full name of month in Enflish
	 */
	private String convertFromRusToEng(String month) {
		return months.get(month);
	}

	/**
	 * Fills {@link Map} of months with name of month in
	 * Russian like a key and in English like value
	 */
	private void fillMonthMap() {
		months.put("янв", "january");
		months.put("фев", "february");
		months.put("мар", "march");
		months.put("апр", "april");
		months.put("май", "may");
		months.put("июн", "june");
		months.put("июл", "july");
		months.put("авг", "august");
		months.put("сен", "september");
		months.put("окт", "october");
		months.put("ноя", "november");
		months.put("дек", "december");
	}

	public static void main(String[] args) {
		DateConverter dateConverter = new DateConverter();
		System.out.println(dateConverter.convertInLong("сегодня, 8:45"));
	}
}
