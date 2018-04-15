package com.isa.instaticketapi.web.rest.vm.FanZoneResource;

import java.util.List;

import com.isa.instaticketapi.domain.Offer;

public class OffersResponse {
	
	private List<Offer> offers;

	public OffersResponse(List<Offer> offers) {
		this.offers = offers;
	}

	
	
	
	
	public List<Offer> getOffers() {
		return offers;
	}





	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}





	@Override
	public String toString() {
		return "OffersResponse [offers=" + offers + "]";
	}
	
	
	
	
	

}
