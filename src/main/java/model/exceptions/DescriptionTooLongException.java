package model.exceptions;

public class DescriptionTooLongException extends RuntimeException {

	public DescriptionTooLongException(){
		super("Please, be a little shorter in the description.");
	}
}
