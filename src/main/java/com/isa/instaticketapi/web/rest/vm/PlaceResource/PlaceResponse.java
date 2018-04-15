package com.isa.instaticketapi.web.rest.vm.PlaceResource;

import com.isa.instaticketapi.domain.Place;

public class PlaceResponse {

	private Place place;

	public PlaceResponse() {}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public PlaceResponse(Place place) {
		this.place = place;
	}
}
