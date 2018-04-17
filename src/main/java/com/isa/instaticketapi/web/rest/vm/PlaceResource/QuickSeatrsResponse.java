package com.isa.instaticketapi.web.rest.vm.PlaceResource;

import java.util.ArrayList;

import com.isa.instaticketapi.domain.Seat;

public class QuickSeatrsResponse {

	private ArrayList<Seat> seats;

	public QuickSeatrsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuickSeatrsResponse(ArrayList<Seat> seats) {
		super();
		this.seats = seats;
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}

}
