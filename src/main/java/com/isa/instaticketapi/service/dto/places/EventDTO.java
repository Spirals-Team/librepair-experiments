package com.isa.instaticketapi.service.dto.places;

import java.time.Instant;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class EventDTO {

	private Long id;

	@NotBlank
	@Size(min = 1, max = 50)
	private String name;

	@Size(max = 5000)

	private String actors;

	@NotBlank
	@Size(min = 1, max = 50)
	private String type;

	@Size(max = 50)
	private String director;

	@Size(max = 5000)
	private String description;

	private int duration;

	private String idPlace;

	@Size(max = 256)
	private String imageUrl;

	public EventDTO(Long id, String name, String actors, String type, String director, String description,
			int duration, String idPlace, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.actors = actors;
		this.type = type;
		this.director = director;
		this.description = description;
		this.duration = duration;
		this.idPlace = idPlace;
		this.imageUrl = imageUrl;

	}

	public String getIdPlace() {
		return idPlace;
	}

	public void setIdPlace(String idPlace) {
		this.idPlace = idPlace;
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

	public EventDTO() {

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

	public void setDescription(String description) {
		this.description = description;
	}

}
