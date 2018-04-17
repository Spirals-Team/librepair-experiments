package com.isa.instaticketapi.web.rest.vm.Projection;

import com.isa.instaticketapi.domain.Projection;

public class ProjectionResponse {

	private Projection projection;

	public ProjectionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectionResponse(Projection projection) {
		super();
		this.projection = projection;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

}
