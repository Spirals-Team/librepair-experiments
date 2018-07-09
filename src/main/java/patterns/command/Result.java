
package patterns.command;

/**
 * Result of Command execution.
 */
public final class Result implements ResultInterface {

    /** The Constant PASS. */
    public static final Boolean PASS = true;

    /** The Constant FAIL. */
    public static final Boolean FAIL = false;

    /** The value. */
    private Boolean value = false;

    /**
     * Instantiates a new result.
     */
    public Result() {
        this.value = PASS;
    }

    /**
     * Instantiates a new result.
     *
     * result
     *
     * @param result
     *            the result
     */
    public Result(final Boolean result) {
        this.value = result;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.command.ResultInterface#result(patterns.command.ResultInterface)
     */
    @Override
    public boolean getResult() {
        return this.value;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.command.ResultInterface#and(patterns.command.ResultInterface)
     */
    @Override
    public boolean and(final ResultInterface newResult) {
        this.value &= newResult.getResult();
        return this.value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Result [result=%s]", this.value);
    }

}
