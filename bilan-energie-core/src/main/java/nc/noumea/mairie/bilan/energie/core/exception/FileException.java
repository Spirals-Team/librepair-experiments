package nc.noumea.mairie.bilan.energie.core.exception;

import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;


/**
 * Exception sur l'instrospection du class
 * 
 * @author Greg Dujardin
 *
 */
public class FileException extends TechnicalException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * 
	 */
	public FileException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public FileException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur avec message
	 * 
	 * @param message Message de l'exception
	 */
	public FileException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 * 
	 * @param cause Cause de l'exception
	 */
	public FileException(final Throwable cause) {
		super(cause);
	}
}
