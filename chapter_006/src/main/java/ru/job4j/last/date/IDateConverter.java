package ru.job4j.last.date;

/**
 * This interface provides methods for
 * representing dates in form of long numbers
 * @author Yury Matskevich
 */
public interface IDateConverter {
	/**
	 * Gets the start date of the current
	 * year like a long number.
	 * @return A long number representing
	 * the start date of the current year.
	 */
	long getLongOfStartOfCurrentYear();

	/**
	 * Converts a given date from a string format
	 * to a long number
	 * @param str A string representing a date.
	 * @return A long number representing
	 * the current date
	 */
	long convertInLong(String str);
}
