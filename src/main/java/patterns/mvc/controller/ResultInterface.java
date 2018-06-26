
package patterns.mvc.controller;

/**
 * Result Interface.
 */
public interface ResultInterface {

    /**
     * Gets the result.
     *
     * @return the result
     */
    boolean getResult();

    /**
     * Sets the result.
     *
     * @param newResult
     *            the new result
     * @return the result interface
     */
    ResultInterface setResult(final boolean newResult);

    /**
     * Update result.
     *
     * @param newResult
     *            the new result
     * @return the result interface
     */
    ResultInterface updateResult(final boolean newResult);

    /**
     * Update result.
     *
     * @param newResult
     *            the new result
     * @return the result interface
     */
    ResultInterface updateResult(final ResultInterface newResult);

}
