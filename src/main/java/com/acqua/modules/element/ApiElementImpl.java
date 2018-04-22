package com.acqua.modules.element;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.acqua.entities.Element;
import com.acqua.rest.CrudWs;
import com.acqua.rest.requests.DeleteRequest;
import com.acqua.rest.responses.BaseResponse;
import com.acqua.rest.responses.ListResponse;
import com.acqua.rest.responses.ReadResponse;
import com.acqua.services.ElementService;



/**
 * Component for implement the business logic of the Element's API
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Component
public class ApiElementImpl implements CrudWs<Element> {
	
	
	//Logger
	private static final Logger log = LoggerFactory.getLogger(ApiElementImpl.class);
	
	
	@Autowired
	private ElementService elementService;

	
	
	/**
	 * Implementation of service for create a new {@link Element}
	 * 
	 * @param {@link Element} json
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> save(Element element) {
		log.debug("saving a new element");
		
		elementService.save(element);
		
		log.debug("saved new element with id {}", element.getId());
		
		BaseResponse response = new BaseResponse(HttpStatus.CREATED.toString(), 
				"New resource has been created", 
				"saved new element '"+element.getDescription()+"' with id '"+element.getId()+"'");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	

	
	/**
	 * Implementation of service for get a {@link Element} by its id
	 * 
	 * @param {@link String} unique id
	 * 
	 * @return {@link ResponseEntity<ReadResponse<Element>>} response
	 */
	@Override
	public ResponseEntity<ReadResponse<Element>> read(String id) {
		
		log.debug("request element with id '{}'", id);
		
		ReadResponse<Element> body = new ReadResponse<>();
		ResponseEntity<ReadResponse<Element>> response = null;
		
		if (StringUtils.isNotBlank(id)) {
			
			Element element = elementService.findById(id);
			
			if (element != null) {
				log.debug("Element with id '{}' exist", id);
				body.setItem(element);
				body.setStatusCode(HttpStatus.OK.toString());
				body.setMessage("Element with id "+id+" exist");
				body.setStatusMessage("OK");
				response = new ResponseEntity<>(body, HttpStatus.OK);
			} else {
				log.debug("Element with id '{}' not found", id);
				body.setStatusCode(HttpStatus.NOT_FOUND.toString());
				body.setMessage("Element with id "+id+" not found");
				response = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
			}
		} else {
			log.debug("Parameter ID is not present");
			body.setStatusCode(HttpStatus.BAD_REQUEST.toString());
			body.setStatusMessage("ID is mandatory");
			body.setMessage("ID parameter is mandatory");
			response = new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

	
	
	/**
	 * Implementation of a service that update an {@link Element} object
	 * 
	 * @param {@link Element} to update
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> update(Element element) {
		
		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		log.debug("updating element with id {}", element.getId());
		
		elementService.save(element);
		
		body.setStatusCode(HttpStatus.OK.toString());
		body.setMessage("Element with id "+element.getId()+" updated");
		body.setStatusMessage("OK");
		
		log.debug("update element with id {}", element.getId());
		
		response = new ResponseEntity<>(body, HttpStatus.OK);
		
		return response;
	}

	
	
	/**
	 * Implementation of a service for delete an {@link Element}
	 * 
	 * @param deleteRequest {@link DeleteRequest} request
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> delete(DeleteRequest deleteRequest) {
		
		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		Element element = elementService.findById(deleteRequest.getId());
		
		if (element != null) {
			elementService.delete(element);
			
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("OK");
			body.setMessage("Element with id "+deleteRequest.getId()+" deleted successfully");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			body.setStatusCode(HttpStatus.NOT_FOUND.toString());
			body.setStatusMessage("Not found");
			body.setMessage("Element with id "+deleteRequest.getId()+" not found");
			response = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	
	
	/**
	 * Implementation of a service that list all {@link Element} stored in database
	 * 
	 * @return {@link ResponseEntity<ListResponse<Element>>} response
	 */
	@Override
	public ResponseEntity<ListResponse<Element>> list() {
		
		ListResponse<Element> body = new ListResponse<>();
		ResponseEntity<ListResponse<Element>> response = null;
		List<Element> list = elementService.list();
		
		body.setItems(list);
		
		if (list.isEmpty()) {
			body.setMessage("Elements collections is empty");
			body.setStatusCode(HttpStatus.NO_CONTENT.toString());
			body.setStatusMessage("No content");
			response = new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
		} else {
			body.setMessage("Found "+list.size()+" elements");
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("Ok");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		}
		
		return response;
	}

}
