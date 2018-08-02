package com.cts.iiht.fsd.skilltracker.skill.controller;

import java.util.List;

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

import com.cts.iiht.fsd.skilltracker.skill.service.SkillService;
import com.cts.iiht.fsd.skilltracker.skill.to.SkillTO;

@RestController
@RequestMapping("/skilltracker/skills")
@CrossOrigin
public class SkillController {
	
	@Autowired
	private SkillService skillService;
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SkillTO> getSkillsList(){
		return skillService.getSkillsList();
	}
	
	@GetMapping(value = "/{skillId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public SkillTO getSkillById(@PathVariable("skillId") Long skillId) {
		return skillService.getSkillById(skillId);
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Long saveSkill(@RequestBody SkillTO skillTO) {
		return skillService.saveSkill(skillTO);
	}
	
	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Long updateSkill(@RequestBody SkillTO skillTO) {
		return skillService.saveSkill(skillTO);
	}
	
	@DeleteMapping(value = "/{skillId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteSkill(@PathVariable("skillId") Long skillId) {
		return skillService.deleteSkill(skillId);
	}

}
