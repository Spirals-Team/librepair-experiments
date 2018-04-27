package nc.noumea.mairie.bilan.energie.contract.exceptions;

import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;


/**
 * Exception lors de l'intégration
 * 
 * @author Greg Dujardin
 *
 */
public class IntegrationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * 
	 */
	public IntegrationException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public IntegrationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur avec message
	 * 
	 * @param message Message de l'exception
	 */
	public IntegrationException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 * 
	 * @param cause Cause de l'exception
	 */
	public IntegrationException(final Throwable cause) {
		super(cause);
	}
}
