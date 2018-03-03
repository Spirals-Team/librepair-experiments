package scilib.checkers;

import scilib.objects.TeamData;

/**
 * Interface of a Checker object, which checks if a {@link TeamData} object meets its conditions.
 * @author Auxiliatrix
 *
 */
public interface Checker {
	
	/**
	 * Tests if the given {@link TeamData} object meets this Checker's condition.
	 * @param td - {@link TeamData} object to be checked against this Checker
	 * @return true if the given {@link TeamData} meets this Checker's condition.
	 */
	public boolean check(TeamData td);
	
	/**
	 * Tests if this Checker is equal to another Checker.
	 * @param c - Checker input to test against
	 * @return true if the given Checker is equal to this checker.
	 */
	public boolean equals(Checker c);
	
	/**
	 * Tests if this Checker is equal to another Checker's String representation.
	 * @param s - String representation of another Checker
	 * @return true if the Checker the given String is representing is equal to this Checker.
	 */
	public boolean equals(String s);
	
	/**
	 * Converts this Checker into a String.
	 * @return this Checker as a String. Returns an empty String if invalid.
	 */
	public String toString();

}
