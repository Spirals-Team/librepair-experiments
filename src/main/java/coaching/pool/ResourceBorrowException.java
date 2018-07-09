
package coaching.pool;

/**
 * ResourceBorrowException Class.
 */
@SuppressWarnings("serial")
public class ResourceBorrowException extends ResourcePoolException {

    /**
     * Instantiates a new resource borrow exception.
     *
     * exception message
     *
     * @param exceptionMessage
     *            the exception message
     */
    public ResourceBorrowException(final String exceptionMessage) {
        super(exceptionMessage);
    }

    /**
     * Instantiates a new resource borrow exception.
     *
     * exception
     *
     * @param exception
     *            the exception
     */
    public ResourceBorrowException(final Exception exception) {
        super(exception);
    }

}
