package com.isa.instaticketapi.web.rest.vm.HallResponse;

import java.util.List;

import com.isa.instaticketapi.domain.Hall;

public class HallsResponse {
	private List<Hall> halls;

	public HallsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Hall> getHalls() {
		return halls;
	}

	public void setHalls(List<Hall> halls) {
		this.halls = halls;
	}

	public HallsResponse(List<Hall> halls) {
		super();
		this.halls = halls;
	}

}
