package com.isa.instaticketapi.service.dto.projection;

public class VoteForEventDTO {
	private int vote;
	private String event;

	public VoteForEventDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoteForEventDTO(int vote, String event) {
		super();
		this.vote = vote;
		this.event = event;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
