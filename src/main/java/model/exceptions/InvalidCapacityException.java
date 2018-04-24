package model.exceptions;

public class InvalidCapacityException extends RuntimeException {

	public InvalidCapacityException(){
		super("Please, insert a valid capacity");
	}
}
