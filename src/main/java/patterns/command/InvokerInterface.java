
package patterns.command;

/**
 * GOF Command Pattern.
 */
public interface InvokerInterface {

    /**
     * Execute.
     *
     * @param actionName
     *            the action name
     * @return the result interface
     * @throws MissingCommandException
     *             the missing command exception
     */
    ResultInterface execute(final String actionName) throws MissingCommandException;

}
