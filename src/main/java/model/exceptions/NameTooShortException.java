package model.exceptions;

public class NameTooShortException extends RuntimeException{

	 public NameTooShortException() {
	        super("The name is too short!");
	    }
}
