package model.exceptions;

public class NoAddressException extends RuntimeException {

	public NoAddressException(){
		super("Please, insert an address.");
	}
}
