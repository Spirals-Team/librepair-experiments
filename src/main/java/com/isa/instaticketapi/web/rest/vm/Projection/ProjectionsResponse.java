package com.isa.instaticketapi.web.rest.vm.Projection;

import java.util.ArrayList;

import com.isa.instaticketapi.domain.Projection;

public class ProjectionsResponse {
	ArrayList<Projection> projections;

	public ProjectionsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectionsResponse(ArrayList<Projection> projections) {
		super();
		this.projections = projections;
	}

	public ArrayList<Projection> getProjections() {
		return projections;
	}

	public void setProjections(ArrayList<Projection> projections) {
		this.projections = projections;
	}
	
	
}
