package com.acqua.modules.department;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.acqua.entities.Department;
import com.acqua.rest.CrudWs;
import com.acqua.rest.requests.DeleteRequest;
import com.acqua.rest.responses.BaseResponse;
import com.acqua.rest.responses.ListResponse;
import com.acqua.rest.responses.ReadResponse;
import com.acqua.services.DepartmentService;



/**
 * Component for implement the business logic of the Department's API
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Component
public class ApiDepartmentImpl implements CrudWs<Department> {
	
	
	//Logger
	private static final Logger log = LoggerFactory.getLogger(ApiDepartmentImpl.class);
	
	
	@Autowired
	private DepartmentService departmentService;

	
	
	/**
	 * Implementation of service for create a new {@link Department}
	 * 
	 * @param {@link Department} json
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> save(Department department) {
		log.debug("saving a new department");
		
		departmentService.save(department);
		
		log.debug("saved new department with id {}", department.getId());
		
		BaseResponse response = new BaseResponse(HttpStatus.CREATED.toString(), 
				"New resource has been created", 
				"saved new department '"+department.getName()+"' with id '"+department.getId()+"'");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	

	
	/**
	 * Implementation of service for get a {@link Department} by its id
	 * 
	 * @param {@link String} unique id
	 * 
	 * @return {@link ResponseEntity<ReadResponse<Department>>} response
	 */
	@Override
	public ResponseEntity<ReadResponse<Department>> read(String id) {
		
		log.debug("request department with id '{}'", id);
		
		ReadResponse<Department> body = new ReadResponse<>();
		ResponseEntity<ReadResponse<Department>> response = null;
		
		if (StringUtils.isNotBlank(id)) {
			
			Department department = departmentService.findById(id);
			
			if (department != null) {
				log.debug("Department with id '{}' exist", id);
				body.setItem(department);
				body.setStatusCode(HttpStatus.OK.toString());
				body.setMessage("Department with id "+id+" exist");
				body.setStatusMessage("OK");
				response = new ResponseEntity<>(body, HttpStatus.OK);
			} else {
				log.debug("Department with id '{}' not found", id);
				body.setStatusCode(HttpStatus.NOT_FOUND.toString());
				body.setMessage("Department with id "+id+" not found");
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
	 * Implementation of a service that update an {@link Department} object
	 * 
	 * @param {@link Department} to update
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> update(Department department) {
		
		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		log.debug("updating department with id {}", department.getId());
		
		departmentService.save(department);
		
		body.setStatusCode(HttpStatus.OK.toString());
		body.setMessage("Department with id "+department.getId()+" updated");
		body.setStatusMessage("OK");
		
		log.debug("update department with id {}", department.getId());
		
		response = new ResponseEntity<>(body, HttpStatus.OK);
		
		return response;
	}

	
	
	/**
	 * Implementation of a service for delete an {@link Department}
	 * 
	 * @param deleteRequest {@link DeleteRequest} request
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> delete(DeleteRequest deleteRequest) {
		
		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		Department department = departmentService.findById(deleteRequest.getId());
		
		if (department != null) {
			departmentService.delete(department);
			
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("OK");
			body.setMessage("Department with id "+deleteRequest.getId()+" deleted successfully");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			body.setStatusCode(HttpStatus.NOT_FOUND.toString());
			body.setStatusMessage("Not found");
			body.setMessage("Department with id "+deleteRequest.getId()+" not found");
			response = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	
	
	/**
	 * Implementation of a service that list all {@link Department} stored in database
	 * 
	 * @return {@link ResponseEntity<ListResponse<Department>>} response
	 */
	@Override
	public ResponseEntity<ListResponse<Department>> list() {
		
		ListResponse<Department> body = new ListResponse<>();
		ResponseEntity<ListResponse<Department>> response = null;
		List<Department> list = departmentService.list();
		
		body.setItems(list);
		
		if (list.isEmpty()) {
			body.setMessage("Department collections is empty");
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
