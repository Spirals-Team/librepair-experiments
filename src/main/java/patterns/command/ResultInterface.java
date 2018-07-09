
package patterns.command;

/**
 * Result Interface.
 */
public interface ResultInterface {

    /**
     * Get the result of the operation.
     *
     * @return the result as a boolean
     */
    boolean getResult();

    /**
     * And.
     *
     * @param execute
     *            the execute
     * @return true, if successful, otherwise false.
     */
    boolean and(final ResultInterface execute);

}
