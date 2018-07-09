
package patterns.command;

/**
 * ConditionalCommand Class.
 */
public class ConditionalCommand extends AbstractCommand {

    /** The predicate. */
    private final boolean predicate = true;

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.AbstractCommand#execute(patterns.command.
     * ParametersInterface)
     */
    @Override
    public ResultInterface execute(final ParametersInterface commandParameters) {
        if (isTrue()) {
            return super.execute(commandParameters);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.AbstractCommand#undo(patterns.command.
     * ParametersInterface)
     */
    @Override
    public ResultInterface undo(final ParametersInterface commandParameters) {
        return super.execute(commandParameters);
    }

    /**
     * Predicate.
     *
     * @return true, if successful, otherwise false.
     */
    private boolean isTrue() {
        return predicate;
    }
}
