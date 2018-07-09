package coaching.net;

/**
 * ApplicationException Class.
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	/**
	 * Instantiates a new application exception.
	 *
	 * message
	 */
	public ApplicationException(final String message) {
		super(message);
	}

	public ApplicationException(final Exception exception) {
		super(exception);
	}

}
