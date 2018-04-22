package com.acqua.rest.responses;

import java.util.List;



/**
 * Standard response for list a items
 * 
 * 
 * @author Christian Lusardi
 * @version 1.0
 * @param <T>
 *
 */
public class ListResponse<T> extends BaseResponse {
	
	private List<T> items;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
