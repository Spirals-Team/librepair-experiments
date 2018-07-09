package idioms;

/**
 * Factory Method Example.
 */
public class FactoryMethod {

	/**
	 * Creates instance of class from fully qualified name.
	 *
	 * string
	 * exception
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String create() throws Exception {
		return (String) Class.forName("java.lang.String").newInstance();
	}

}
