package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity vote for place
 * 
 * @author Milica
 *
 */
@Entity
@Table(name = "VoteForPlace")
public class VoteForPlace implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "vote")
	private int vote;

	@ManyToOne(fetch = FetchType.EAGER)
	private Place place;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public VoteForPlace(Long id, int vote, Place place, User user) {
		super();
		this.id = id;
		this.vote = vote;
		this.place = place;
		this.user = user;
	}

	public VoteForPlace() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
