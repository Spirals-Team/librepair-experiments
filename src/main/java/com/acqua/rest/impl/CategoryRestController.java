package com.acqua.rest.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acqua.constants.ApiConstants;
import com.acqua.constants.PathConstants;
import com.acqua.entities.Category;
import com.acqua.entities.Element;
import com.acqua.modules.category.ApiCategoryImpl;
import com.acqua.rest.CrudWs;
import com.acqua.rest.ResourceManagementWs;
import com.acqua.rest.requests.AssociationRequest;
import com.acqua.rest.requests.AssociationRequestExt;
import com.acqua.rest.requests.DeleteRequest;
import com.acqua.rest.responses.BaseResponse;
import com.acqua.rest.responses.ListResponse;
import com.acqua.rest.responses.ReadResponse;



/**
 * Rest controller for expose the API
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@RestController
@RequestMapping(path=ApiConstants.API_V1_CATEGORY)
public class CategoryRestController implements CrudWs<Category>, ResourceManagementWs<Category, Element> {
	
	@Autowired
	private ApiCategoryImpl apiCategoryImpl;

	
	
	/**
	 * Service for create a new {@link Category}
	 * 
	 * @param {@link Category} json
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@PostMapping(PathConstants.CREATE)
	@Override
	public ResponseEntity<BaseResponse> save(@Valid @RequestBody Category category) {
		return apiCategoryImpl.save(category);
	}
	
	
	
	/**
	 * Service for get a {@link Category} by its id
	 * 
	 * @param {@link ReadRequest} request
	 * 
	 * @response {@link ReadResponse} response
	 */
	@GetMapping(PathConstants.READ+"/{id}")
	@Override
	public ResponseEntity<ReadResponse<Category>> read(@PathVariable String id) {
		return apiCategoryImpl.read(id);
	}
	
	
	
	/**
	 * Service for update a {@link Category}
	 * 
	 * @param  {@link Category} request
	 * 
	 * @response {@link BaseResponse} response
	 */
	@PatchMapping(PathConstants.UPDATE)
	@Override
	public ResponseEntity<BaseResponse> update(@Valid @RequestBody Category category) {
		return apiCategoryImpl.update(category);
	}


	
	/**
	 * Service for delete an {@link Category}
	 * 
	 * @param deleteRequest {@link DeleteRequest} request
	 * 
	 * @return {@link BaseResponse} response
	 */
	@DeleteMapping(PathConstants.DELETE)
	@Override
	public ResponseEntity<BaseResponse> delete(@Valid @RequestBody DeleteRequest deleteRequest) {
		return apiCategoryImpl.delete(deleteRequest);
	}
	
	
	
	/**
	 * Service for list all {@link Category} stored in database
	 * 
	 * @return {@link ListResponse<Category>} response
	 */
	@GetMapping(PathConstants.LIST)
	@Override
	public ResponseEntity<ListResponse<Category>> list() {
		return apiCategoryImpl.list();
	}


	
	/**
	 * Service to associate entity {@link Element} to a container entity identified by its id
	 * 
	 * @param request {@link AssociationRequest<Element>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@PutMapping(PathConstants.ASSOCIATE)
	@Override
	public ResponseEntity<BaseResponse> associateEntity(@Valid @RequestBody AssociationRequest<Element> request) {
		return apiCategoryImpl.associateEntity(request);
	}



	/**
	 * Service to associate entity {@link Element} to a container entity {@link Category}
	 * 
	 * @param request {@link AssociationRequestExt<Category, Element>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@PutMapping(PathConstants.ASSOCIATE_EXT)
	@Override
	public ResponseEntity<BaseResponse> associateEntity(@Valid @RequestBody AssociationRequestExt<Category, Element> request) {
		return apiCategoryImpl.associateEntity(request);
	}

}
