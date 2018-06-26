
package patterns.interpreter;

/**
 * Non Terminal Expression Class.
 */
public class NonTerminalExpression extends AbstractExpression {

    /**
     * Instantiates a new non terminal expression.
     */
    public NonTerminalExpression() {
        super();
    }

    /**
     * Instantiates a new non terminal expression.
     *
     * @param result
     *            the result
     */
    public NonTerminalExpression(final boolean result) {
        super(result);
    }

}
