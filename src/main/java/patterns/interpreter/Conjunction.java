
package patterns.interpreter;

/**
 * Conjunction / And Expression Class.
 */
public class Conjunction extends NonTerminalExpression {

    /** The lhs. */
    private final AbstractExpression lhs;

    /** The rhs. */
    private final AbstractExpression rhs;

    /**
     * Instantiates a new and expression.
     *
     * @param lhs
     *            sub-expression on left hand side.
     * @param rhs
     *            sub-expression on right hand side.
     */
    public Conjunction(final AbstractExpression lhs, final AbstractExpression rhs) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.interpreter.NonTerminalExpression#interpret(patterns.interpreter
     * .ContextInterface)
     */
    @Override
    public boolean interpret(final Context context) {
        return lhs.interpret(context) && rhs.interpret(context);
    }

}
