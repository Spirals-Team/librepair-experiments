package model;

import model.exceptions.NoCoordsEnoughException;
import model.exceptions.TimeOutOfRangeException;
import model.exceptions.UserBlockedException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Post {

	private Vehicle vehicle;
	private User ownerUser;
	private Coord pickUpCoord;
	private List<Coord> returnCoords= new ArrayList<Coord>();
	private LocalDateTime sinceDate;
	private LocalDateTime UntilDate;
	private double costPerDay;
	private int phone;

	
	public Post(){}

	public Post(Vehicle vehicle, User user, Coord pickUpCoord, List<Coord> returnCoords2,
                LocalDateTime sinceDate, LocalDateTime UntilDate, double costPerDay){

		long days= sinceDate.until(UntilDate, ChronoUnit.DAYS);

		if( days<1 || days>5 ){
			throw new TimeOutOfRangeException();
		}
		if(!user.isEnabled()){
		    throw new UserBlockedException();
        }
		if(pickUpCoord==null || returnCoords2.isEmpty()){
			throw new NoCoordsEnoughException();
		}
		
		this.vehicle=vehicle;
		this.ownerUser=user;
		this.pickUpCoord=pickUpCoord;
		this.returnCoords=returnCoords2;
        this.sinceDate = sinceDate;
		this.UntilDate = UntilDate;
		//this.phone = phone;
        this.costPerDay = costPerDay;
	}
	
	public double getCostPerDay(){
		return this.costPerDay;
	}

	public User getUser() {
		return this.ownerUser;
	}

}
