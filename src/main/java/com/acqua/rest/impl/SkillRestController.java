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
import com.acqua.entities.Skill;
import com.acqua.modules.skill.ApiSkillImpl;
import com.acqua.rest.CrudWs;
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
@RequestMapping(path=ApiConstants.API_V1_SKILL)
public class SkillRestController implements CrudWs<Skill> {
	
	
	
	@Autowired
	private ApiSkillImpl apiSkillImpl;
	
	
	
	/**
	 * Service for create a new {@link Skill}
	 * 
	 * @param {@link BaseResponse} json
	 */
	@PostMapping(PathConstants.CREATE)
	@Override
	public ResponseEntity<BaseResponse> save(@Valid @RequestBody Skill skill) {
		return apiSkillImpl.save(skill);
	}
	
	
	
	/**
	 * Service for get a {@link Skill} by its id
	 * 
	 * @param {@link ReadRequest} request
	 * 
	 * @response {@link ReadResponse} response
	 */
	@GetMapping(PathConstants.READ+"/{id}")
	@Override
	public ResponseEntity<ReadResponse<Skill>> read(@PathVariable String id) {
		return apiSkillImpl.read(id);
	}
	
	
	
	/**
	 * Service for update a {@link Skill}
	 * 
	 * @param  {@link Skill} request
	 * 
	 * @response {@link BaseResponse} response
	 */
	@PatchMapping(PathConstants.UPDATE)
	@Override
	public ResponseEntity<BaseResponse> update(@Valid @RequestBody Skill skill) {
		return apiSkillImpl.update(skill);
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
		return apiSkillImpl.delete(deleteRequest);
	}
	
	
	
	/**
	 * Service for list all {@link Skill} stored in database
	 * 
	 * @return {@link ListResponse<Skill>} response
	 */
	@GetMapping(PathConstants.LIST)
	@Override
	public ResponseEntity<ListResponse<Skill>> list() {
		return apiSkillImpl.list();
	}

}
