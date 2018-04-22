package model.interfaces;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IUserState {
	
	
	Post post(Vehicle vehicle, User user, Coord pickUpCoord,ArrayList<Coord>returnCoords,
			  LocalDateTime sinceDate, LocalDateTime untilDate, double costPerHour);

	Reservation rent(Post post, LocalDateTime reservationSinceDate,
					 LocalDateTime reservationUntilDate, User tenantUser);//ver cual va a ser la penalizacion

	boolean isEnabled();
}
