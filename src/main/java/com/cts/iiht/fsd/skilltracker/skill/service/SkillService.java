package com.cts.iiht.fsd.skilltracker.skill.service;

import java.util.List;

import com.cts.iiht.fsd.skilltracker.skill.to.SkillTO;

public interface SkillService {

	public List<SkillTO> getSkillsList();
	
	public SkillTO getSkillById(Long skillId);
	
	public Long saveSkill(SkillTO skillTO);
	
	public boolean deleteSkill(Long skillId);
}
