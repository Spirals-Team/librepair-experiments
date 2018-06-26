
package coaching.idioms;

/**
 * Condition Class.
 */
public class Condition implements ConditionInterface<Boolean> {

    /** The result. */
    private boolean result = false;

    /**
     * Instantiates a new condition.
     *
     * @param result
     *            the result
     */
    protected Condition(final boolean result) {
        this.result = result;
    }

    /**
     * Checks if is true.
     *
     * @return the condition
     */
    public boolean isTrue() {
        return this.result;
    }

    /**
     * Checks if is false.
     *
     * @return the condition
     */
    public boolean isFalse() {
        return this.result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Condition [result=%s]", this.result);
    }

}
