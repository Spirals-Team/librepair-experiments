package com.acqua.rest.responses;


/**
 * Standard response for read request
 * 
 * @author Christian Lusardi
 * @version 1.0
 * @param <T>
 *
 */
public class ReadResponse<T> extends BaseResponse {
	
	private T item;

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

}
