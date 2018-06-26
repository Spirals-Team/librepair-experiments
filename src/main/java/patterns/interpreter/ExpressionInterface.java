
package patterns.interpreter;

/**
 * Expression Interface.
 */
public interface ExpressionInterface {

    /**
     * interpret.
     *
     * @param context
     *            the context
     * @return true, if successful, otherwise false., otherwise false.
     */
    boolean interpret(final Context context);
}
