package com.isa.instaticketapi.web.rest.vm.FanZoneResource;

import com.isa.instaticketapi.domain.Bid;
import com.isa.instaticketapi.domain.Item;

public class BidResponse {
	
	private Bid bid;
	
	public BidResponse() {
		
	}

	
	

	public BidResponse(Bid bid) {
		super();
		this.bid = bid;
	}




	public Bid getBid() {
		return bid;
	}




	public void setBid(Bid bid) {
		this.bid = bid;
	}


	

	
}
