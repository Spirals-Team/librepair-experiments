package io.xrates.backend.exceptions;

public class RateProviderException extends Exception{
	
	static final long serialVersionUID = 1L;

	public RateProviderException (String error) {
		super(error);
	}
}
