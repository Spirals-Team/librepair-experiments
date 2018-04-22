package com.acqua.rest.requests;

import javax.validation.constraints.NotNull;

/**
 * Extended association request
 * 
 * @author Christian Lusardi
 *
 * @param <T> container object
 * 
 * @param <O> item object
 */
public class AssociationRequestExt<T, O> extends AssociationRequest<O> {
	
	@NotNull
	private T containerObject;
	
	
	public AssociationRequestExt() {
		super();
	}

	public AssociationRequestExt(T containerObject) {
		super();
		this.containerObject = containerObject;
	}

	public T getContainerObject() {
		return containerObject;
	}

	public void setContainerObject(T containerObject) {
		this.containerObject = containerObject;
	}
	
	

}
