package nc.noumea.mairie.radi.service;

public class AdWrapperServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7511356177168987559L;
	
	public AdWrapperServiceException() {
		super();
	}
	
	public AdWrapperServiceException(String message) {
		super(message);
	}
	
	public AdWrapperServiceException(String message, Throwable innerException) {
		super(message, innerException);
	}

}
