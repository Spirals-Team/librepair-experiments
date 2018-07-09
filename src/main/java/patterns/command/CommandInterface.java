
package patterns.command;

/**
 * Command Interface from GOF Command Pattern.
 */
public interface CommandInterface {

    /**
     * Execute.
     *
     * @param commandParameters
     *            the command parameters
     * @return the result interface
     */
    ResultInterface execute(final ParametersInterface commandParameters);

    /**
     * Undo.
     *
     * @param commandParameters
     *            the command parameters
     * @return the result interface
     */
    ResultInterface undo(final ParametersInterface commandParameters);

    /**
     * Result.
     *
     * @return the boolean
     */
    Boolean getResult();

}
