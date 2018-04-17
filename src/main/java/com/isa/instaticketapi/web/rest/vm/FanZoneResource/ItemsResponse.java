package com.isa.instaticketapi.web.rest.vm.FanZoneResource;

import java.util.List;

import com.isa.instaticketapi.domain.Item;

public class ItemsResponse {
	
	private List<Item> items;
	

	public ItemsResponse(List<Item> items) {
		super();
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
	

}
