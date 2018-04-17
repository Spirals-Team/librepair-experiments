package com.isa.instaticketapi.service.dto;

public class ChangeOfferDTO {

	
private Long id;
	
	private String name;
	
	private String description;
	
	private String image;
	
	
	public ChangeOfferDTO() {
		
	}
	
	
	
	
	
	public ChangeOfferDTO(String name, String description, String image) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
	}


	


	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public String getImage() {
		return image;
	}





	public void setImage(String image) {
		this.image = image;
	}

	
	
	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}






	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
