package com.isa.instaticketapi.web.rest.vm.HallResponse;

import com.isa.instaticketapi.domain.Hall;

public class HallResponse {

	private Hall hall;

	public HallResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HallResponse(Hall hall) {
		super();
		this.hall = hall;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

}
