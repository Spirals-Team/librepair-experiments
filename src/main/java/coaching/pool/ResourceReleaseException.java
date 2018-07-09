
package coaching.pool;

/**
 * ResourceReleaseException Class.
 */
@SuppressWarnings("serial")
public class ResourceReleaseException extends ResourcePoolException {

    /**
     * Instantiates a new resource release exception.
     *
     * @param exceptionMessage
     *            the exception message
     */
    public ResourceReleaseException(final String exceptionMessage) {
        super(exceptionMessage);
    }

}
