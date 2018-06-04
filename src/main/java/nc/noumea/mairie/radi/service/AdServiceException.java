package nc.noumea.mairie.radi.service;

public class AdServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -239836003554227535L;

	public AdServiceException() {
		super();
	}
	
	public AdServiceException(String message) {
		super(message);
	}
	
	public AdServiceException(String message, Throwable innerException) {
		super(message, innerException);
	}
}
