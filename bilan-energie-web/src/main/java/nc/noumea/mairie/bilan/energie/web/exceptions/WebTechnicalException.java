package nc.noumea.mairie.bilan.energie.web.exceptions;

import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Erreur technique Web
 * 
 * @author David ALEXIS
 * 
 */
public class WebTechnicalException extends TechnicalException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de la super class
	 */
	public WebTechnicalException() {
		super();
	}

	/**
	 * Constructeur de la super class
	 * 
	 * @param message Message de l'exception
	 * @param cause Cause de l'exception
	 */
	public WebTechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur de la super class
	 * 
	 * @param message Message de l'exception
	 */
	public WebTechnicalException(String message) {
		super(message);
	}

	/**
	 * Constructeur de la super class
	 * 
	 * @param cause Cause de l'exception
	 */
	public WebTechnicalException(Throwable cause) {
		super(cause);
	}

}
