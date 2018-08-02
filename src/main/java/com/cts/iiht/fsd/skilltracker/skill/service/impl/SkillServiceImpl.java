package com.cts.iiht.fsd.skilltracker.skill.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateSkillsRepository;
import com.cts.iiht.fsd.skilltracker.framework.entity.Skill;
import com.cts.iiht.fsd.skilltracker.skill.repository.SkillRepository;
import com.cts.iiht.fsd.skilltracker.skill.service.SkillService;
import com.cts.iiht.fsd.skilltracker.skill.to.SkillTO;

@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private AssociateSkillsRepository associateSkillsRepository;

	@Override
	public List<SkillTO> getSkillsList() {
		List<Skill> skillsList = skillRepository.findAll();
		List<SkillTO> skillTOList = new ArrayList<SkillTO>();
		if(skillsList != null && !skillsList.isEmpty()) {
			Iterator<Skill> skillListItr = skillsList.iterator();
			while(skillListItr.hasNext()) {
				Skill skill = skillListItr.next();
				SkillTO skillTO = new SkillTO();
				skillTO.setSkillId(skill.getSkillId());
				skillTO.setSkillName(skill.getSkillName());
				skillTOList.add(skillTO);
			}
		}
		return skillTOList;
	}

	@Override
	public SkillTO getSkillById(Long skillId) {
		SkillTO skillTO = new SkillTO();
		Optional<Skill> skillObj = skillRepository.findById(skillId);
		if(skillObj != null && skillObj.isPresent()) {
			Skill skill = skillObj.get();
			skillTO.setSkillId(skill.getSkillId());
			skillTO.setSkillName(skill.getSkillName());
		}
		return skillTO;
	}

	@Override
	public Long saveSkill(SkillTO skillTO) {
		Skill skill = null;
		if(StringUtils.isEmpty(skillTO.getSkillId())) {
			skill = new Skill();
		} else {
			skill = skillRepository.findById(skillTO.getSkillId()).get();
		}
		skill.setSkillName(skillTO.getSkillName());
		Skill savedSkillObj = skillRepository.save(skill);
		return savedSkillObj.getSkillId();
	}

	@Override
	public boolean deleteSkill(Long skillId) {
		Optional<Skill> skillObj = skillRepository.findById(skillId); 
		if(skillObj != null && skillObj.isPresent()) {
			Skill skill = skillObj.get();
			if(skill.getSkillsAssociatesSet() != null && !skill.getSkillsAssociatesSet().isEmpty()) {
				associateSkillsRepository.deleteAll(skill.getSkillsAssociatesSet());	
			}
			skillRepository.delete(skill);
		} else {
			return false;
		}
		return true;
	}
}
