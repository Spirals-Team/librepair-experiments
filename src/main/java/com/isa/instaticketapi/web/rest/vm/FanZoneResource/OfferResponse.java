package com.isa.instaticketapi.web.rest.vm.FanZoneResource;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.Offer;

public class OfferResponse {

	private Offer offer;
	
	
	public OfferResponse() {
		
	}

	
	

	public OfferResponse(Offer offer) {
		super();
		this.offer = offer;
	}




	public Offer getOffer() {
		return offer;
	}




	public void setOffer(Offer offer) {
		this.offer = offer;
	}


	

	
	
}
