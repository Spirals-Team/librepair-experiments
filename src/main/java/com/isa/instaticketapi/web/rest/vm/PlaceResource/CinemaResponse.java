package com.isa.instaticketapi.web.rest.vm.PlaceResource;

import java.util.List;

import com.isa.instaticketapi.domain.Place;

public class CinemaResponse {
	private List<Place> cinemas;

	public CinemaResponse() {}

	public CinemaResponse(List<Place> cinemas) {
		this.cinemas = cinemas;
	}

	public List<Place> getCinemas() {
		return cinemas;
	}

	public void setCinemas(List<Place> cinemas) {
		this.cinemas = cinemas;
	}

}
