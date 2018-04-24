package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import model.interfaces.IUserState;

public class UserEnabled implements IUserState{

	public Reservation rent(Post post, LocalDateTime reservationSinceDate,
					 LocalDateTime reservationUntilDate, User tenantUser) {

		return new Reservation(post, reservationSinceDate, reservationUntilDate, tenantUser);
	}

	public Post post(Vehicle vehicle, User user, Coord pickUpCoord, ArrayList<Coord> returnCoords,
					 LocalDateTime sinceDate, LocalDateTime untilDate, double costPerHour) {
		return new Post(vehicle,user,pickUpCoord,returnCoords, sinceDate, untilDate,costPerHour);
	}

	public boolean isEnabled(){
		return true;
	}

	/*
	private double calculationCostOfRent(double costPerHour, LocalDateTime reservationSinceDate,
										 LocalDateTime reservationUntilDate){
		long hours= reservationSinceDate.until(reservationUntilDate, ChronoUnit.HOURS);
		return hours*costPerHour;
	}
	*/

}
