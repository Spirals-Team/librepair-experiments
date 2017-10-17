package spoon.test.comment.testclasses;

import java.util.ArrayList;

/**
 * JavaDoc test class.
 *
 * Long description
 *
 * @deprecated
 * @since 1.3
 * @author Thomas Durieux
 * @version 1.0
 */
public class JavaDocComment {

	/**
	 * Field javadoc
	 *
	 * @since 1.3
	 */
	public Object field = new ArrayList<>();

	/**
	 * Javadoc without tags
	 */
	public void m() {

	}

	/**
	 * With tags
	 *
	 * @param i the parameters
	 * @return the return type
	 * @throws RuntimeException an exception
	 */
	public Object m(int i) throws RuntimeException {
		return null;
	}
}