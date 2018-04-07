package model.exceptions;

public class NoCoordsEnoughException extends RuntimeException {

	public NoCoordsEnoughException(){
		super("Please, set one coord to pick up and one coord to return at least.");
	}
}
