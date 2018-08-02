package com.cts.iiht.fsd.skilltracker.associate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.iiht.fsd.skilltracker.associate.service.AssociateService;
import com.cts.iiht.fsd.skilltracker.associate.to.AssociateTO;

@RestController
@RequestMapping("/skilltracker/associates")
@CrossOrigin
public class AssociateController {
	
	@Autowired
	private AssociateService associateService;
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public AssociateTO getAssociatesList(){
		return associateService.getAssociatesList();
	}
	
	@GetMapping(value = "/{associateId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AssociateTO getAssociateById(@PathVariable("associateId") Long associateId) {
		return associateService.getAssociateById(associateId);
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Long saveAssociate(@RequestBody AssociateTO associateTO) {
		return associateService.saveAssociate(associateTO);
	}
	
	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Long updateAssociate(@RequestBody AssociateTO associateTO) {
		return associateService.saveAssociate(associateTO);
	}
	
	@DeleteMapping(value = "/{associateId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteAssociate(@PathVariable("associateId") Long associateId) {
		return associateService.deleteAssociate(associateId);
	}
	
	@GetMapping(value = "/skills/", produces = MediaType.APPLICATION_JSON_VALUE)
	public AssociateTO getAssociateSkills() {
		return associateService.getAssociateSkills();
	}

}
