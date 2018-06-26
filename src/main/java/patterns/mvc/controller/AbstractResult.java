
package patterns.mvc.controller;

/**
 * AbstractResult class.
 */
public abstract class AbstractResult implements ResultInterface {

    /** The result. */
    private boolean result;

    /**
     * Instantiates a new abstract result.
     */
    public AbstractResult() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.mvc.controller.ResultInterface#result()
     */
    @Override
    public boolean getResult() {
        return this.result;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.mvc.controller.ResultInterface#updateResult(patterns.mvc.
     * controller.ResultInterface)
     */
    @Override
    public ResultInterface setResult(final boolean newResult) {
        this.result = newResult;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.mvc.controller.ResultInterface#updateResult(boolean)
     */
    @Override
    public ResultInterface updateResult(final boolean newResult) {
        this.result &= newResult;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.mvc.controller.ResultInterface#updateResult(patterns.mvc.
     * controller.ResultInterface)
     */
    @Override
    public ResultInterface updateResult(final ResultInterface newResult) {
        this.result &= newResult.getResult();
        return this;
    }
}
