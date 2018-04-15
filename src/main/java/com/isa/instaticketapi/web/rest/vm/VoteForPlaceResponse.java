package com.isa.instaticketapi.web.rest.vm;

public class VoteForPlaceResponse {
	private int vote;

	public VoteForPlaceResponse(int vote) {
		super();
		this.vote = vote;
	}

	public VoteForPlaceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

}
