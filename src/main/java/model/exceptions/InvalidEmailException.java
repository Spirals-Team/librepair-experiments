package model.exceptions;

public class InvalidEmailException extends RuntimeException{
	
	public InvalidEmailException(){
		super ("Please, enter a valid email");
	}

}
