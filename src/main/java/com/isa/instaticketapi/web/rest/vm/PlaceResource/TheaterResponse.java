package com.isa.instaticketapi.web.rest.vm.PlaceResource;

import java.util.List;

import com.isa.instaticketapi.domain.Place;

public class TheaterResponse {
	private List<Place> theaters;

	public TheaterResponse() {
	}

	public List<Place> getTheaters() {
		return theaters;
	}

	public void setTheaters(List<Place> theaters) {
		this.theaters = theaters;
	}

	public TheaterResponse(List<Place> theaters) {
		this.theaters = theaters;
	}

}
