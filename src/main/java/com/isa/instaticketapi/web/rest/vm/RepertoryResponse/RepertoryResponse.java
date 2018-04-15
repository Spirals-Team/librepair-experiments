package com.isa.instaticketapi.web.rest.vm.RepertoryResponse;

import java.util.ArrayList;

import com.isa.instaticketapi.domain.Repertory;

public class RepertoryResponse {

	private ArrayList<Repertory> repertories;

	public RepertoryResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepertoryResponse(ArrayList<Repertory> repertories) {
		super();
		this.repertories = repertories;
	}

	public ArrayList<Repertory> getRepertories() {
		return repertories;
	}

	public void setRepertories(ArrayList<Repertory> repertories) {
		this.repertories = repertories;
	}

}
