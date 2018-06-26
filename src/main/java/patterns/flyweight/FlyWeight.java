
package patterns.flyweight;

/**
 * FlyWeight Class.
 */
public final class FlyWeight {

    /** The common state. */
    private String commonState;

    /**
     * private constructor to prevent uncontrolled creation.
     */
    @SuppressWarnings("unused")
    private FlyWeight() {
        super();
    }

    /**
     * Instantiates a new fly weight.
     *
     * @param commonState
     *            the common state
     */
    public FlyWeight(final String commonState) {
        this.commonState = commonState;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("FlyWeight [commonState=%s]", this.commonState);
    }

}
