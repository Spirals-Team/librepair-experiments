package nc.noumea.mairie.bilan.energie.contract.exceptions;

import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;


/**
 * Exception de validation métier
 * 
 * @author Josselin
 *
 */
public class BusinessValidationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 *
	 */
	public BusinessValidationException() {
		super();
	}

	/**
	 * Constructeur avec message et cause
	 *
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public BusinessValidationException(final String messageKey, final Throwable cause) {
		super(messageKey, cause);
	}

	/**
	 * Constructeur avec message
	 *
	 * @param message Message de l'exception
	 */
	public BusinessValidationException(final String message) {
		super(message);
	}

	/**
	 * Constructeur avec cause
	 *
	 * @param cause Cause de l'exception
	 */
	public BusinessValidationException(final Throwable cause) {
		super(cause);
	}
}
