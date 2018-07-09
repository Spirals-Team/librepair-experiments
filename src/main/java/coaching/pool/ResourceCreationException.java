
package coaching.pool;

/**
 * ResourceCreationException Class.
 */
@SuppressWarnings("serial")
public class ResourceCreationException extends ResourcePoolException {

    /**
     * Instantiates a new resource creation exception.
     *
     * exception message
     *
     * @param exceptionMessage
     *            the exception message
     */
    public ResourceCreationException(final String exceptionMessage) {
        super(exceptionMessage);
    }

}
