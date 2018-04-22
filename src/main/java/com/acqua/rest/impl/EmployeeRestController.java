package com.acqua.rest.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acqua.constants.ApiConstants;
import com.acqua.constants.PathConstants;
import com.acqua.entities.Employee;
import com.acqua.entities.Skill;
import com.acqua.modules.employee.ApiEmployeeImpl;
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
@RequestMapping(path=ApiConstants.API_V1_EMPLOYEE)
public class EmployeeRestController implements CrudWs<Employee>, ResourceManagementWs<Employee, Skill> {
	
	
	
	@Autowired
	private ApiEmployeeImpl apiEmployeeImpl;
	
	
	
	/**
	 * Service for create a new {@link Employee}
	 * 
	 * @param {@link BaseResponse} json
	 */
	@PostMapping(PathConstants.CREATE)
	@Override
	public ResponseEntity<BaseResponse> save(@Valid @RequestBody Employee employee) {
		return apiEmployeeImpl.save(employee);
	}
	
	
	
	/**
	 * Service for get a {@link Employee} by its id
	 * 
	 * @param {@link ReadRequest} request
	 * 
	 * @response {@link ReadResponse} response
	 */
	@GetMapping(PathConstants.READ+"/{id}")
	@Override
	public ResponseEntity<ReadResponse<Employee>> read(@PathVariable String id) {
		return apiEmployeeImpl.read(id);
	}
	
	
	
	/**
	 * Service for update a {@link Employee}
	 * 
	 * @param  {@link Employee} request
	 * 
	 * @response {@link BaseResponse} response
	 */
	@PatchMapping(PathConstants.UPDATE)
	@Override
	public ResponseEntity<BaseResponse> update(@Valid @RequestBody Employee employee) {
		return apiEmployeeImpl.update(employee);
	}


	
	/**
	 * 
	 * @param deleteRequest {@link DeleteRequest} request
	 * 
	 * @return {@link BaseResponse} response
	 */
	@DeleteMapping(PathConstants.DELETE)
	@Override
	public ResponseEntity<BaseResponse> delete(@Valid @RequestBody DeleteRequest deleteRequest) {
		return apiEmployeeImpl.delete(deleteRequest);
	}
	
	
	
	/**
	 * Service for list all {@link Employee} stored in database
	 * 
	 * @return {@link ListResponse<Employee>} response
	 */
	@GetMapping(PathConstants.LIST)
	@Override
	public ResponseEntity<ListResponse<Employee>> list() {
		return apiEmployeeImpl.list();
	}



	/**
	 * Service to associate entity {@link Skill} to a container entity identified by its id
	 * 
	 * @param request {@link AssociationRequest<Skill>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> associateEntity(AssociationRequest<Skill> request) {
		return apiEmployeeImpl.associateEntity(request);
	}



	/**
	 * Service to associate entity {@link Skill} to a container entity {@link Employee}
	 * 
	 * @param request {@link AssociationRequestExt<Employee, Skill>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> associateEntity(AssociationRequestExt<Employee, Skill> request) {
		return apiEmployeeImpl.associateEntity(request);
	}

}
