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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity od projection.
 * 
 * @author Milica Kovacevic
 *
 */
@Entity
@Table(name = "Event")
public class Event extends AbstractAuditingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 60)
	@Column(name = "name", length = 60)
	private String name;

	@Size(max = 5000)
	@Column(name = "actors", length = 5000)
	private String actors;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "type", length = 50)
	private String type;

	@Size(max = 50)
	@Column(name = "director", length = 50)
	private String director;

	@Column(name = "duration", length = 4)
	private int duration;

	@Size(max = 256)
	@Column(name = "image_url", length = 256)
	private String imageUrl;

	@Size(max = 5000)
	@Column(name = "description", length = 5000)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	private Place place;

	private int vote = 0;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String string) {
		this.description = string;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
