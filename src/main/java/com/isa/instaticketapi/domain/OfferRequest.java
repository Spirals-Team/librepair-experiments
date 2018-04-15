package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="OfferRequest")
public class OfferRequest  extends AbstractAuditingEntity implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Offer offer;
	
	private Boolean accepted;
	
	
	public OfferRequest() {
		// TODO Auto-generated constructor stub
		
		this.accepted = false;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Offer getOffer() {
		return offer;
	}


	public void setOffer(Offer offer) {
		this.offer = offer;
	}


	public Boolean getAccepted() {
		return accepted;
	}


	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}
	
	
	
	
	

}
