
package patterns.chain;

/**
 * AbstractRequest Class.
 */
public abstract class AbstractRequest implements RequestInterface {

    /** The payload. */
    private String payload;

    /**
     * payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * payload.
     *
     * new payload
     *
     * @param payload
     *            the new payload
     */
    public void setPayload(final String payload) {
        this.payload = payload;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [payload=%s]", this.getClass().getSimpleName(), payload);
    }

}
