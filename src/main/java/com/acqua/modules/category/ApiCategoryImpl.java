package com.acqua.modules.category;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.acqua.entities.Category;
import com.acqua.entities.Element;
import com.acqua.rest.CrudWs;
import com.acqua.rest.ResourceManagementWs;
import com.acqua.rest.requests.AssociationRequest;
import com.acqua.rest.requests.AssociationRequestExt;
import com.acqua.rest.requests.DeleteRequest;
import com.acqua.rest.responses.BaseResponse;
import com.acqua.rest.responses.ListResponse;
import com.acqua.rest.responses.ReadResponse;
import com.acqua.services.CategoryService;



/**
 * Component for implement the business logic of the Category's API
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Component
public class ApiCategoryImpl implements CrudWs<Category>, ResourceManagementWs<Category, Element> {
	
	
	
	//Logger
	private static final Logger log = LoggerFactory.getLogger(ApiCategoryImpl.class);
	
	
	@Autowired
	private CategoryService categoryService;
	
	
	/**
	 * Implementation of service to save new {@link Category}
	 * 
	 * @param categroy {@link Category} to store in database
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} base response
	 */
	@Override
	public ResponseEntity<BaseResponse> save(Category category) {
		log.debug("saving a new category");
		
		categoryService.save(category);
		
		log.debug("saved new category with id {}", category.getId());
		
		BaseResponse response = new BaseResponse(HttpStatus.CREATED.toString(), 
				"New resource has been created", 
				"saved new category '"+category.getName()+"' with id '"+category.getId()+"'");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
	
	/**
	 * Implementation of service to get a {@link Category} by its id
	 * 
	 * @param {@link String} unique id
	 * 
	 * @return {@link ResponseEntity<ReadResponse<Category>>} response
	 */
	@Override
	public ResponseEntity<ReadResponse<Category>> read(String id) {

		log.debug("request category with id '{}'", id);
		
		ReadResponse<Category> body = new ReadResponse<>();
		ResponseEntity<ReadResponse<Category>> response = null;
		
		if (StringUtils.isNotBlank(id)) {
			
			Category category = categoryService.findById(id);
			
			if (category != null) {
				log.debug("category with id '{}' exist", id);
				body.setItem(category);
				body.setStatusCode(HttpStatus.OK.toString());
				body.setMessage("category with id "+id+" exist");
				body.setStatusMessage("OK");
				response = new ResponseEntity<>(body, HttpStatus.OK);
			} else {
				log.debug("category with id '{}' not found", id);
				body.setStatusCode(HttpStatus.NOT_FOUND.toString());
				body.setMessage("category with id "+id+" not found");
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
	 * Implementation of a service that update an {@link Category} object
	 * 
	 * @param category {@link Category} to update
	 * 
	 * @return {@link ResponseEntity<Category>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> update(Category category) {

		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		log.debug("updating category with id {}", category.getId());
		
		categoryService.save(category);
		
		body.setStatusCode(HttpStatus.OK.toString());
		body.setMessage("category with id "+category.getId()+" updated");
		body.setStatusMessage("OK");
		
		log.debug("update category with id {}", category.getId());
		
		response = new ResponseEntity<>(body, HttpStatus.OK);
		
		return response;
	}

	
	
	/**
	 * Implementation of a service for delete an {@link Category}
	 * 
	 * @param deleteRequest {@link DeleteRequest} request
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> delete(DeleteRequest deleteRequest) {

		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		Category category = categoryService.findById(deleteRequest.getId());
		
		if (category != null) {
			categoryService.delete(category);
			
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("OK");
			body.setMessage("category with id "+deleteRequest.getId()+" deleted successfully");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			body.setStatusCode(HttpStatus.NOT_FOUND.toString());
			body.setStatusMessage("Not found");
			body.setMessage("category with id "+deleteRequest.getId()+" not found");
			response = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	
	
	/**
	 * Implementation of a service that list all {@link Category} stored in database
	 * 
	 * @return {@link ResponseEntity<ListResponse<Category>>} response
	 */
	@Override
	public ResponseEntity<ListResponse<Category>> list() {
		ListResponse<Category> body = new ListResponse<>();
		ResponseEntity<ListResponse<Category>> response = null;
		List<Category> list = categoryService.list();
		
		body.setItems(list);
		
		if (list.isEmpty()) {
			body.setMessage("categories collections is empty");
			body.setStatusCode(HttpStatus.NO_CONTENT.toString());
			body.setStatusMessage("No content");
			response = new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
		} else {
			body.setMessage("Found "+list.size()+" categories");
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("Ok");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		}
		
		return response;
	}



	/**
	 * Implementation of a service to associate entity {@link Element} to a container entity identified by its id
	 * 
	 * @param request {@link AssociationRequest<Element>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> associateEntity(AssociationRequest<Element> request) {
		categoryService.associate(request.getIdOfContainerObject(), request.getObjectToAssociate());
		
		ResponseEntity<BaseResponse> response = new ResponseEntity<BaseResponse>(
				new BaseResponse(HttpStatus.OK.toString(), 
						"OK", 
						"Element with it '"+request.getObjectToAssociate().getId()+"' was associated successfully to a Category with id '"+request.getIdOfContainerObject()+"'"), 
				HttpStatus.OK);
		
		return response;
	}



	/**
	 * Implementation of a service to associate entity {@link Element} to a container entity {@link Category}
	 * 
	 * @param request {@link AssociationRequestExt<Category, Element>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> associateEntity(AssociationRequestExt<Category, Element> request) {
		
		categoryService.associate(request.getContainerObject(), request.getObjectToAssociate());
		
		ResponseEntity<BaseResponse> response = new ResponseEntity<BaseResponse>(
				new BaseResponse(HttpStatus.OK.toString(), 
						"OK", 
						"Element with it '"+request.getObjectToAssociate().getId()+"' was associated successfully to a Category with id '"+request.getIdOfContainerObject()+"'"), 
				HttpStatus.OK);
		
		return response;
	}
	
	
}
