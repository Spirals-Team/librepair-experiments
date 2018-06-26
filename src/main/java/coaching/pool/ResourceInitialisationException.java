
package coaching.pool;

/**
 * ResourceInitialisationException Class.
 */
@SuppressWarnings("serial")
public class ResourceInitialisationException extends ResourcePoolException {

    /**
     * Instantiates a new resource initialisation exception.
     *
     * exception message
     *
     * @param exceptionMessage
     *            the exception message
     */
    public ResourceInitialisationException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}
