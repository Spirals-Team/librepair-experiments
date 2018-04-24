package model.exceptions;

public class NameTooLongException extends RuntimeException {

	 public NameTooLongException() {
	        super("The name is too long");
	    }
}
