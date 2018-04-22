package com.acqua.rest;

import org.springframework.http.ResponseEntity;

import com.acqua.rest.requests.AssociationRequest;
import com.acqua.rest.requests.AssociationRequestExt;
import com.acqua.rest.responses.BaseResponse;



/**
 * Interface to define management API for {@link T} container object and {@link O} object
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public interface ResourceManagementWs<T, O> {
	
	
	
	/**
	 * Function to associate entity {@link O} to a container entity identified by its id
	 * 
	 * @param request {@link AssociationRequest<O>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	ResponseEntity<BaseResponse> associateEntity(AssociationRequest<O> request);
	
	
	
	/**
	 * Function to associate entity {@link O} to a container entity {@link T}
	 * 
	 * @param request {@link AssociationRequestExt<T, O>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	ResponseEntity<BaseResponse> associateEntity(AssociationRequestExt<T,O> request);
	
	
}
