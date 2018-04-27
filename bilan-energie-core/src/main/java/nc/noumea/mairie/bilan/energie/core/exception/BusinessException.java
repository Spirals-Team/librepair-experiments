package nc.noumea.mairie.bilan.energie.core.exception;

/**
 * Exception Métier Abstraite
 * 
 * @author Greg Dujardin
 *
 */
public abstract class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 */
	public BusinessException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur avec message
	 * 
	 * @param message Message de l'exception
	 */
	public BusinessException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 * 
	 * @param cause Cause de l'exception
	 */
	public BusinessException(final Throwable cause) {
		super(cause);
	}

}
