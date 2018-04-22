package com.acqua.modules.skill;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.acqua.entities.Skill;
import com.acqua.rest.CrudWs;
import com.acqua.rest.requests.DeleteRequest;
import com.acqua.rest.responses.BaseResponse;
import com.acqua.rest.responses.ListResponse;
import com.acqua.rest.responses.ReadResponse;
import com.acqua.services.SkillService;



/**
 * Component for implement the business logic of the Skill's API
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
@Component
public class ApiSkillImpl implements CrudWs<Skill> {
	
	
	
	//Logger
	private static final Logger log = LoggerFactory.getLogger(ApiSkillImpl.class);
	
	
	@Autowired
	private SkillService skillService;
	
	
	/**
	 * Implementation of service to save new {@link Skill}
	 * 
	 * @param categroy {@link Skill} to store in database
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} base response
	 */
	@Override
	public ResponseEntity<BaseResponse> save(Skill skill) {
		log.debug("saving a new skill");
		
		skillService.save(skill);
		
		log.debug("saved new skill with id {}", skill.getId());
		
		BaseResponse response = new BaseResponse(HttpStatus.CREATED.toString(), 
				"New resource has been created", 
				"saved new skill  with id '"+skill.getId()+"'");
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
	
	/**
	 * Implementation of service to get a {@link Skill} by its id
	 * 
	 * @param {@link String} unique id
	 * 
	 * @return {@link ResponseEntity<ReadResponse<Skill>>} response
	 */
	@Override
	public ResponseEntity<ReadResponse<Skill>> read(String id) {

		log.debug("request skill with id '{}'", id);
		
		ReadResponse<Skill> body = new ReadResponse<>();
		ResponseEntity<ReadResponse<Skill>> response = null;
		
		if (StringUtils.isNotBlank(id)) {
			
			Skill skill = skillService.findById(id);
			
			if (skill != null) {
				log.debug("skill with id '{}' exist", id);
				body.setItem(skill);
				body.setStatusCode(HttpStatus.OK.toString());
				body.setMessage("skill with id "+id+" exist");
				body.setStatusMessage("OK");
				response = new ResponseEntity<>(body, HttpStatus.OK);
			} else {
				log.debug("skill with id '{}' not found", id);
				body.setStatusCode(HttpStatus.NOT_FOUND.toString());
				body.setMessage("skill with id "+id+" not found");
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
	 * Implementation of a service that update an {@link Skill} object
	 * 
	 * @param skill {@link Skill} to update
	 * 
	 * @return {@link ResponseEntity<Skill>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> update(Skill skill) {

		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		log.debug("updating skill with id {}", skill.getId());
		
		skillService.save(skill);
		
		body.setStatusCode(HttpStatus.OK.toString());
		body.setMessage("skill with id "+skill.getId()+" updated");
		body.setStatusMessage("OK");
		
		log.debug("updated skil with id {}", skill.getId());
		
		response = new ResponseEntity<>(body, HttpStatus.OK);
		
		return response;
	}

	
	
	/**
	 * Implementation of a service for delete an {@link Skill}
	 * 
	 * @param deleteRequest {@link DeleteRequest} request
	 * 
	 * @return {@link ResponseEntity<BaseResponse>} response
	 */
	@Override
	public ResponseEntity<BaseResponse> delete(DeleteRequest deleteRequest) {

		BaseResponse body = new BaseResponse();
		ResponseEntity<BaseResponse> response = null;
		
		Skill skill = skillService.findById(deleteRequest.getId());
		
		if (skill != null) {
			skillService.delete(skill);
			
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("OK");
			body.setMessage("skill with id "+deleteRequest.getId()+" deleted successfully");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			body.setStatusCode(HttpStatus.NOT_FOUND.toString());
			body.setStatusMessage("Not found");
			body.setMessage("skill with id "+deleteRequest.getId()+" not found");
			response = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}

	
	
	/**
	 * Implementation of a service that list all {@link Skill} stored in database
	 * 
	 * @return {@link ResponseEntity<ListResponse<Skill>>} response
	 */
	@Override
	public ResponseEntity<ListResponse<Skill>> list() {
		ListResponse<Skill> body = new ListResponse<>();
		ResponseEntity<ListResponse<Skill>> response = null;
		List<Skill> list = skillService.list();
		
		body.setItems(list);
		
		if (list.isEmpty()) {
			body.setMessage("skills collections is empty");
			body.setStatusCode(HttpStatus.NO_CONTENT.toString());
			body.setStatusMessage("No content");
			response = new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
		} else {
			body.setMessage("Found "+list.size()+" skills");
			body.setStatusCode(HttpStatus.OK.toString());
			body.setStatusMessage("Ok");
			response = new ResponseEntity<>(body, HttpStatus.OK);
		}
		
		return response;
	}


	
	
}
