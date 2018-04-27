package nc.noumea.mairie.bilan.energie.core.exception;

/**
 * Exception Technique Abstraite
 * 
 * @author Greg Dujardin
 *
 */
public abstract class TechnicalException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 */
	public TechnicalException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public TechnicalException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur avec message
	 * 
	 * @param message Message de l'exception
	 */
	public TechnicalException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 * 
	 * @param cause Cause de l'exception
	 */
	public TechnicalException(final Throwable cause) {
		super(cause);
	}
}
