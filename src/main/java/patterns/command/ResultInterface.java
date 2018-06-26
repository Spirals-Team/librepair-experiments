
package patterns.command;

/**
 * Result Interface.
 */
public interface ResultInterface {

    /**
     * Result.
     *
     * @return the boolean
     */
    Boolean getResult();

    /**
     * And.
     *
     * @param execute
     *            the execute
     * @return true, if successful, otherwise false.
     */
    boolean and(final ResultInterface execute);

}
