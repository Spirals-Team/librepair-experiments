package nc.noumea.mairie.bilan.energie.contract.exceptions;

import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;


/**
 * Exception sur l'instrospection du class
 * 
 * @author Greg Dujardin
 *
 */
public class TooManyValueException extends BusinessException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * 
	 */
	public TooManyValueException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public TooManyValueException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur avec message
	 * 
	 * @param message Message de l'exception
	 */
	public TooManyValueException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 * 
	 * @param cause Cause de l'exception
	 */
	public TooManyValueException(final Throwable cause) {
		super(cause);
	}
}
