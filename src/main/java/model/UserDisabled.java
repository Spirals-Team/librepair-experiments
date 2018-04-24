package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.exceptions.UserBlockedException;
import model.interfaces.IUserState;

public class UserDisabled implements IUserState{

	private LocalDateTime untilDate=null;

	public UserDisabled(){
		this.untilDate = LocalDateTime.now().plusDays(3L);
	}


	public Reservation rent(Post post, LocalDateTime reservationSinceDate,
					 LocalDateTime reservationUntilDate, User tenantUser) {

		if(this.untilDate.isAfter(LocalDateTime.now())){
			throw new UserBlockedException();
		}else{
			post.getUser().enabledUser();
			return post.getUser().rent(post,reservationSinceDate,reservationUntilDate);
		}


	}

	public Post post(Vehicle vehicle, User user, Coord pickUpCoord, ArrayList<Coord> returnCoords,
					 LocalDateTime sinceDate, LocalDateTime untilDate, double costPerHour) {
		throw new UserBlockedException();
	}

	public boolean isEnabled(){
		return false;
	}

}
