package com.isa.instaticketapi.service.dto.places;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class PlaceDTO {
	
	
	private Long id;
	
	@NotBlank
	@Size(min = 1, max = 50)
	private String name;
	
	
	private String address;
	
	
	private String description;
	
	
	private String type;
	
	
	
	public PlaceDTO(Long id, String name, String address, String description, String type) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.type = type;
	}



	public PlaceDTO() {
		// TODO Auto-generated constructor stub
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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	

}
