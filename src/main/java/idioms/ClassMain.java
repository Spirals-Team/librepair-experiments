
package idioms;

/**
 * Example of Main entry point when run from command line.
 */
public class ClassMain {

	/**
	 * main method.
	 *
	 * arguments
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		for (final String string : args) {
			System.out.println(string);
		}

		System.out.println("%s = " + Constants.COUNTRY_NAME);
	}
}
