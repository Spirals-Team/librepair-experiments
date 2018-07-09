
package coaching.pool;

/**
 * ResourcePoolException Class.
 */
@SuppressWarnings("serial")
public class ResourcePoolException extends Exception {

    /**
     * Instantiates a new resource pool exception.
     *
     * @param message
     *            the message
     */
    public ResourcePoolException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new resource pool exception.
     *
     * @param exception
     *            the exception
     */
    public ResourcePoolException(final Exception exception) {
        super(exception);
    }

}
