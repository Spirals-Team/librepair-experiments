
package patterns.interpreter;

/**
 * Disjunction / OR Expression Class.
 */
public class Disjunction extends NonTerminalExpression {

    /** The lhs. */
    private final AbstractExpression lhs;

    /** The rhs. */
    private final AbstractExpression rhs;

    /**
     * Instantiates a new or expression.
     *
     * @param lhs
     *            sub-expression on left hand side.
     * @param rhs
     *            sub-expression on right hand side.
     */
    public Disjunction(final AbstractExpression lhs, final AbstractExpression rhs) {
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
        return lhs.interpret(context) || rhs.interpret(context);
    }

}
