
package coaching.pool;

/**
 * ResourcePoolCreationException Class.
 */
@SuppressWarnings("serial")
public class ResourcePoolCreationException extends ResourcePoolException {

    /**
     * Instantiates a new resource pool creation exception.
     *
     * exception message
     *
     * @param exceptionMessage
     *            the exception message
     */
    public ResourcePoolCreationException(final String exceptionMessage) {
        super(exceptionMessage);
    }

    /**
     * Instantiates a new resource pool creation exception.
     *
     * exception
     *
     * @param exception
     *            the exception
     */
    public ResourcePoolCreationException(final Exception exception) {
        super(exception);
    }

}
