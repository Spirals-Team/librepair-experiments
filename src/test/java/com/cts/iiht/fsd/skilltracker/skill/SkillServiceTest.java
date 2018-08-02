package com.cts.iiht.fsd.skilltracker.skill;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateSkillsRepository;
import com.cts.iiht.fsd.skilltracker.framework.entity.Associate;
import com.cts.iiht.fsd.skilltracker.framework.entity.AssociateSkills;
import com.cts.iiht.fsd.skilltracker.framework.entity.Skill;
import com.cts.iiht.fsd.skilltracker.skill.repository.SkillRepository;
import com.cts.iiht.fsd.skilltracker.skill.service.impl.SkillServiceImpl;
import com.cts.iiht.fsd.skilltracker.skill.to.SkillTO;

public class SkillServiceTest {

	private Skill skill;
	
	private Optional<Skill> skillOptionalObj;
	
	private List<Skill> skillList = new ArrayList<Skill>();
	
	@Mock
	private SkillRepository skillRepository;
	
	@Mock
	private AssociateSkillsRepository associateSkillsRepository;
	
	@InjectMocks
	private SkillServiceImpl skillService;
	
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		skill = new Skill();
		skill.setSkillId(1L);
		skill.setSkillName("Skill 1");
		skillList.add(skill);
		skillOptionalObj = Optional.of(skill);
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull("Skill service injection failed.", skillService);
	}
	
	@Test
	public void testGetSkillById() throws Exception {
		when(skillRepository.findById(1L)).thenReturn(skillOptionalObj);
		SkillTO skillTOObj = skillService.getSkillById(1L);
		assertEquals(skillOptionalObj.get().getSkillName(), skillTOObj.getSkillName());
	}
	
	@Test
	public void testSaveSkill() throws Exception {
		Skill newSkill = new Skill();
		newSkill.setSkillId(null);
		newSkill.setSkillName("Skill 1");
		SkillTO newSkillTO = new SkillTO();
		newSkillTO.setSkillId(null);
		newSkillTO.setSkillName("Skill 1");
		when(skillRepository.save(newSkill)).thenReturn(skill);
		Long skillId = skillService.saveSkill(newSkillTO);
		assertNotNull(skillId);
	}
	
	@Test
	public void testUpdateSkill() throws Exception {
		Skill updateSkill = new Skill();
		updateSkill.setSkillId(1L);
		updateSkill.setSkillName("Updated Skill");
		SkillTO updateSkillTO = new SkillTO();
		updateSkillTO.setSkillId(1L);
		updateSkillTO.setSkillName("Updated Skill");
		when(skillRepository.findById(1L)).thenReturn(skillOptionalObj);
		when(skillRepository.save(updateSkill)).thenReturn(skill);
		Long skillId = skillService.saveSkill(updateSkillTO);
		assertNotNull(skillId);
	}
	
	@Test
	public void testDeleteSkill() throws Exception {
		AssociateSkills associateSkill = new AssociateSkills();
		associateSkill.setId(100L);
		associateSkill.setSkill(skill);
		associateSkill.setAssociate(new Associate(200L));
		associateSkill.setSkillLevel(10L);
		Set<AssociateSkills> associateSkillSet = new HashSet<AssociateSkills>();
		associateSkillSet.add(associateSkill);
		skill.setSkillsAssociatesSet(associateSkillSet);
		skillOptionalObj = Optional.of(skill);
		when(skillRepository.findById(1L)).thenReturn(skillOptionalObj);
		doNothing().when(associateSkillsRepository).deleteAll(skill.getSkillsAssociatesSet());
		doNothing().when(skillRepository).delete(skill);
	}
}
