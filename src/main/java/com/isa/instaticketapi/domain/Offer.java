package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Offer")
public class Offer extends AbstractAuditingEntity implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(length = 100)
	private String name;
	
	@Column(length = 200)
	private String description;
	
	@Column
	private String image;
	
	@Column
	private String startPrice;
	
	@Column
	private String endDate;
	
	@Column
	private Boolean accepted;
	
	@Column
	private Boolean sold;
	
	@Column
	private String bestPrice;


	public Offer() {
		
		this.accepted = false;
		this.sold = false;
	}
	
	
	public Offer(String name, String description, String image, String startPrice, String endDate) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.startPrice = startPrice;
		this.endDate = endDate;
		this.accepted = false;
		this.sold = false;
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


	public Boolean getAccepted() {
		return accepted;
	}


	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}


	public String getBestPrice() {
		return bestPrice;
	}


	public void setBestPrice(String bestPrice) {
		this.bestPrice = bestPrice;
	}


	public Boolean getSold() {
		return sold;
	}


	public void setSold(Boolean sold) {
		this.sold = sold;
	}
	
	
	
	
	
}
