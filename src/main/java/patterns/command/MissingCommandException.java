
package patterns.command;

/**
 * MissingCommandException Class.
 */
@SuppressWarnings("serial")
public class MissingCommandException extends Exception {

    /**
     * Instantiates a new missing command exception.
     *
     * @param message
     *            the message
     */
    public MissingCommandException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new missing command exception.
     *
     * @param cause
     *            the cause
     */
    public MissingCommandException(final Throwable cause) {
        super(cause);
    }
}
