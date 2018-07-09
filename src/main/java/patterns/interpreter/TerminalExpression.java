
package patterns.interpreter;

/**
 * Terminal Expression Class.
 */
public class TerminalExpression extends AbstractExpression {

    /**
     * Instantiates a new terminal expression.
     */
    public TerminalExpression() {
        super(true);
    }

    /**
     * Instantiates a new terminal expression.
     *
     * @param result
     *            the result
     */
    public TerminalExpression(final boolean result) {
        super(result);
    }

}
