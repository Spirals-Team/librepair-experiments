package com.acqua.modules.employee;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.acqua.entities.Employee;
import com.acqua.entities.Skill;
import com.acqua.rest.CrudWs;
import com.acqua.rest.ResourceManagementWs;
import com.acqua.rest.requests.AssociationRequest;
import com.acqua.rest.requests.AssociationRequestExt;
import com.acqua.rest.requests.DeleteRequest;
import com.acqua.rest.responses.BaseResponse;
import com.acqua.rest.responses.ListResponse;
import com.acqua.rest.responses.ReadResponse;
import com.acqua.services.EmployeeService;



/**
 * Component for implement the business logic of the Employee's API
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Component
public class ApiEmployeeImpl implements CrudWs<Employee>, ResourceManagementWs<Employee, Skill> {
	
	
	//Logger
	private static final Logger log = LoggerFactory.getLogger(ApiEmployeeImpl.class);
	
	
	@Autowired
	private EmployeeService employeeService;

	
	
	/**
	 * Implementation of service for create a new {@link Employee}
	 * 
	 * @param {@link Employee} json
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> save(Employee employee) {
		log.debug("saving a new employee");
		
		employeeService.save(employee);
		
		log.debug("saved new employee with id {}", employee.getId());
		
		BaseResponse response = new BaseResponse(HttpStatus.CREATED.toString(), 
				"New resource has been created", 
				"saved new element with id '"+employee.getId()+"'");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	

	
	/**
	 * Implementation of service for get a {@link Employee} by its id
	 * 
	 * @param {@link String} unique id
	 * 
	 * @return {@link ResponseEntity<ReadResponse<Employee>>} response
	 */
	@Override
	public ResponseEntity<ReadResponse<Employee>> read(String id) {
		
		log.debug("request element with id '{}'", id);
		
		ReadResponse<Employee> body = new ReadResponse<>();
		ResponseEntity<ReadResponse<Employee>> response = null;
		
		if (StringUtils.isNotBlank(id)) {
			
			Employee employee = employeeService.findById(id);
			
			if (employee != null) {
				log.debug("employee with id '{}' exist", id);
				body.setItem(employee);
				body.setStatusCode(HttpStatus.OK.toString());
				body.setMessage("employee with id "+id+" exist");
				body.setStatusMessage("OK");
				response = new ResponseEntity<>(body, HttpStatus.OK);
			} else {
				log.debug("employee with id '{}' not found", id);
				body.setStatusCode(HttpStatus.NOT_FOUND.toString());
				body.setMessage("employee with id "+id+" not found");
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
	 * Implementation of a service that update an {@link Employee} object
	 * 
	 * @param {@link Employee} to update
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> update(Employee employee) {
		
		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		log.debug("updating element with id {}", employee.getId());
		
		employeeService.save(employee);
		
		body.setStatusCode(HttpStatus.OK.toString());
		body.setMessage("Element with id "+employee.getId()+" updated");
		body.setStatusMessage("OK");
		
		log.debug("update element with id {}", employee.getId());
		
		response = new ResponseEntity<>(body, HttpStatus.OK);
		
		return response;
	}

	
	
	/**
	 * Implementation of a service for delete an {@link Employee}
	 * 
	 * @param deleteRequest {@link DeleteRequest} request
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> delete(DeleteRequest deleteRequest) {
		
		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		Employee employee = employeeService.findById(deleteRequest.getId());
		
		if (employee != null) {
			employeeService.delete(employee);
			
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("OK");
			body.setMessage("Employee with id "+deleteRequest.getId()+" deleted successfully");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			body.setStatusCode(HttpStatus.NOT_FOUND.toString());
			body.setStatusMessage("Not found");
			body.setMessage("Employee with id "+deleteRequest.getId()+" not found");
			response = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	
	
	/**
	 * Implementation of a service that list all {@link Employee} stored in database
	 * 
	 * @return {@link ResponseEntity<ListResponse<Employee>>} response
	 */
	@Override
	public ResponseEntity<ListResponse<Employee>> list() {
		
		ListResponse<Employee> body = new ListResponse<>();
		ResponseEntity<ListResponse<Employee>> response = null;
		List<Employee> list = employeeService.list();
		
		body.setItems(list);
		
		if (list.isEmpty()) {
			body.setMessage("Employee collections is empty");
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



	/**
	 * Service to associate entity {@link Skill} to a container entity identified by its id
	 * 
	 * @param request {@link AssociationRequest<Skill>}
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> associateEntity(AssociationRequest<Skill> request) {
		employeeService.associate(request.getIdOfContainerObject(), request.getObjectToAssociate());
		
		ResponseEntity<BaseResponse> response = new ResponseEntity<BaseResponse>(
				new BaseResponse(HttpStatus.OK.toString(), 
						"OK", 
						"Skill with id '"+request.getObjectToAssociate().getId()+"' was associated successfully to an Employee with id '"+request.getIdOfContainerObject()+"'"), 
				HttpStatus.OK);
		
		return response;
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
		employeeService.associate(request.getContainerObject(), request.getObjectToAssociate());
		
		ResponseEntity<BaseResponse> response = new ResponseEntity<BaseResponse>(
				new BaseResponse(HttpStatus.OK.toString(), 
						"OK", 
						"Skill with id '"+request.getObjectToAssociate().getId()+"' was associated successfully to an Employee with id '"+request.getIdOfContainerObject()+"'"), 
				HttpStatus.OK);
		
		return response;
	}

}
