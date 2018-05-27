package spoon.test.comment.testclasses;


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
    public java.lang.Object field = new java.util.ArrayList<>();

    /**
     * Javadoc without tags
     */
    public void m() {
    }

    /**
     * With tags
     *
     * @param i
     * 		the parameters
     * @return the return type
     * @throws RuntimeException
     * 		an exception
     */
    public java.lang.Object m(int i) throws java.lang.RuntimeException {
        return null;
    }
}

