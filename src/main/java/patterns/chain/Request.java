
package patterns.chain;

/**
 * Request Class.
 */
public final class Request extends AbstractRequest {

    /**
     * Instantiates a new request.
     *
     * @param payload
     *            the payload
     */
    public Request(final String payload) {
        super();
        setPayload(payload);
    }

}
