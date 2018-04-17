package com.isa.instaticketapi.service.dto;

public class OfferDTO {
	
	private String name;
	
	private String description;
	
	private String image;
	
	private String startPrice;
	
	private String endDate;
	
	
	public OfferDTO() {
		
	}
	
	
	
	
	
	public OfferDTO(String name, String description, String image, String startPrice, String endDate) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.startPrice = startPrice;
		this.endDate = endDate;
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


	


	public String getStartPrice() {
		return startPrice;
	}





	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}





	public String getEndDate() {
		return endDate;
	}





	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}





	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
