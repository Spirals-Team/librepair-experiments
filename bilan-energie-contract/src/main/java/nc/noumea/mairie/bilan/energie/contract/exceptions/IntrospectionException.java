package nc.noumea.mairie.bilan.energie.contract.exceptions;

import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;


/**
 * Exception sur l'instrospection du class
 * 
 * @author Greg Dujardin
 *
 */
public class IntrospectionException extends TechnicalException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * 
	 */
	public IntrospectionException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public IntrospectionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur avec message
	 * 
	 * @param message Message de l'exception
	 */
	public IntrospectionException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 * 
	 * @param cause Cause de l'exception
	 */
	public IntrospectionException(final Throwable cause) {
		super(cause);
	}
}
