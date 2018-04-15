package com.isa.instaticketapi.service.dto.places;


public class VoteForPlaceDTO {

	private int vote;
	private String place;

	public VoteForPlaceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public VoteForPlaceDTO(int vote, String place) {
		super();
		this.vote = vote;
		this.place = place;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
