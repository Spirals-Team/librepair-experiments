package com.acqua.rest.requests;

import javax.validation.constraints.NotNull;

/**
 * Request for delete object
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public class DeleteRequest {
	
	@NotNull
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}