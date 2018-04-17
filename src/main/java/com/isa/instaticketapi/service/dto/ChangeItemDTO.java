package com.isa.instaticketapi.service.dto;

public class ChangeItemDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private String price;
	
	






	private String image;
	
	
	public ChangeItemDTO() {
		
	}
	
	
	
	
	
	public ChangeItemDTO(String name, String description, String image, String price) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
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


	public String getPrice() {
		return price;
	}





	public void setPrice(String price) {
		this.price = price;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	

}