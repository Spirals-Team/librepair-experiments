package com.acqua.rest.requests;

import javax.validation.constraints.NotNull;

/**
 * Association request for associate Object A to a Object B
 *
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public class AssociationRequest<T> {

	@NotNull
	private String idOfContainerObject;
	
	@NotNull
	private T objectToAssociate;
	
	
	public AssociationRequest() {
		super();
	}

	public AssociationRequest(String idOfContainerObject, T objectToAssociate) {
		super();
		this.idOfContainerObject = idOfContainerObject;
		this.objectToAssociate = objectToAssociate;
	}

	public String getIdOfContainerObject() {
		return idOfContainerObject;
	}

	public void setIdOfContainerObject(String idOfContainerObject) {
		this.idOfContainerObject = idOfContainerObject;
	}

	public T getObjectToAssociate() {
		return objectToAssociate;
	}

	public void setObjectToAssociate(T objectToAssociate) {
		this.objectToAssociate = objectToAssociate;
	}
	
	
	
}
