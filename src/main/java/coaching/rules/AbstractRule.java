
package coaching.rules;

import coaching.context.ContextInterface;

/**
 * AbstractCommaAbstractRulend Class.
 */
public abstract class AbstractRule implements CommandInterface {

    /** The context. */
    protected ContextInterface context = null;

    /**
     * Instantiates a new abstract command.
     *
     * context
     *
     * @param context
     *            the context
     */
    public AbstractRule(final ContextInterface context) {
        this.context = context;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * javamentor.rules.CommandInterface#execute(javamentor.rules.Parameters)
     */
    @Override
    public void execute(final AbstractParameters parameters) {
        final String className = this.getClass().getSimpleName();
        final String message = String.format("%s.execute(%s) must be declared", className, parameters);
        throw new UnsupportedOperationException(message);
    }

}
