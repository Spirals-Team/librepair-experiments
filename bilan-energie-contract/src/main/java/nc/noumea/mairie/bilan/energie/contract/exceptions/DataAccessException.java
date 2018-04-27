package nc.noumea.mairie.bilan.energie.contract.exceptions;

import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;


/**
 * Exception lors de l'accès aux données
 * 
 * @author Greg Dujardin
 *
 */
public class DataAccessException extends TechnicalException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * 
	 */
	public DataAccessException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public DataAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur avec message
	 * 
	 * @param message Message de l'exception
	 */
	public DataAccessException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 * 
	 * @param cause Cause de l'exception
	 */
	public DataAccessException(final Throwable cause) {
		super(cause);
	}
}
