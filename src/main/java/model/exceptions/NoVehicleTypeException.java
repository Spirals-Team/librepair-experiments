package model.exceptions;

public class NoVehicleTypeException extends RuntimeException{

	public NoVehicleTypeException(){
		super ("Please, insert the vehicle type");
	}
}
