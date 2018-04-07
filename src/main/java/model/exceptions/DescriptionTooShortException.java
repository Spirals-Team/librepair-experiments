package model.exceptions;

public class DescriptionTooShortException extends RuntimeException{

	public DescriptionTooShortException(){
		super("Please, insert a longer description.");
	}
}
