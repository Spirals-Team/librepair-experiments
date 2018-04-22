package model;

import model.enums.VehicleType;
import model.exceptions.DescriptionTooLongException;
import model.exceptions.DescriptionTooShortException;
import model.exceptions.InvalidCapacityException;
import model.exceptions.NoVehicleTypeException;

import java.util.ArrayList;

public class Vehicle {
	
	private User owner;
	private VehicleType type;
	private int capacity;
	private String description;
	private ArrayList<String> photos;
	
	/*
	 * Constructors
	 */
	
	public Vehicle(User owner, VehicleType type, int capacity, String description){
//		this.photos=new ArrayList<File>();
		if(type==null){
			throw new NoVehicleTypeException();
		}
		if(capacity==0){
			throw new InvalidCapacityException();
		}
		if(description.length()<=30){
			throw new DescriptionTooShortException();
		}
		if(description.length()>=200){
			throw new DescriptionTooLongException();
		}
		this.type= type;
		this.owner=owner;
		this.capacity=capacity;
		this.description=description;
	}

}
