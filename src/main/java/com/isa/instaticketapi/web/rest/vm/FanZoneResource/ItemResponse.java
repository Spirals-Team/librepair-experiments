package com.isa.instaticketapi.web.rest.vm.FanZoneResource;

import com.isa.instaticketapi.domain.Item;

public class ItemResponse {

	
	private Item item;
	
	
	public ItemResponse() {
		
	}

	
	

	public ItemResponse(Item item) {
		super();
		this.item = item;
	}




	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}
	
	
	
}
