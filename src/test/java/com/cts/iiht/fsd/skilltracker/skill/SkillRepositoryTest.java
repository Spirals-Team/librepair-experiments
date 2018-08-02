package com.cts.iiht.fsd.skilltracker.skill;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.iiht.fsd.skilltracker.SkillTrackerApplication;
import com.cts.iiht.fsd.skilltracker.framework.entity.Skill;
import com.cts.iiht.fsd.skilltracker.skill.repository.SkillRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest(classes = SkillTrackerApplication.class)
@Transactional
public class SkillRepositoryTest {
	
	@Autowired
	private transient SkillRepository skillRepository;
	
	public void setRepo(final SkillRepository repository) {
		this.skillRepository = repository;
	}
	
	@Test
	public void testGetAllSkills() throws Exception{
		Skill skill = new Skill();
		skill.setSkillId(null);
		skill.setSkillName("Skill 1");
		skillRepository.save(skill);
		final List<Skill> skillList = skillRepository.findAll();
		assertNotNull(skillList);
	}
	
	@Test
	public void testGetSkillById() throws Exception {
		Skill skill = new Skill();
		skill.setSkillId(null);
		skill.setSkillName("Skill 1");
		Skill savedObj = skillRepository.save(skill);
		final Optional<Skill> skillObj = skillRepository.findById(savedObj.getSkillId());
		assertNotNull(skillObj);
		assertEquals(skillObj.get().getSkillName(), "Skill 1");
	}
	
	@Test
	public void testSaveSkill() throws Exception {
		Skill skill = new Skill();
		skill.setSkillId(null);
		skill.setSkillName("Skill 1");
		Skill savedObj = skillRepository.save(skill);
		assertNotNull(savedObj);
		assertNotNull(savedObj.getSkillId());
	}
	
	@Test
	public void testUpdateSkill() throws Exception {
		Skill skill = new Skill();
		skill.setSkillId(null);
		skill.setSkillName("Skill 1");
		Skill savedObj = skillRepository.save(skill);
		Skill updatedSkill = new Skill();
		updatedSkill.setSkillId(savedObj.getSkillId());
		updatedSkill.setSkillName("Skill 2");
		Skill updateSkillObj = skillRepository.save(updatedSkill);
		assertNotNull(updateSkillObj);
		assertNotNull(updateSkillObj.getSkillId());
		assertEquals(updateSkillObj.getSkillName(), "Skill 2");
	}
	
	@Test
	public void testDeleteSkill() throws Exception{
		Skill skill = new Skill();
		skill.setSkillId(null);
		skill.setSkillName("Skill 1");
		Skill savedObj = skillRepository.save(skill);
		Skill deleteSkill = new Skill();
		deleteSkill.setSkillId(savedObj.getSkillId());
		deleteSkill.setSkillName("Skill 1");
		skillRepository.delete(deleteSkill);
		final Optional<Skill> skillObj = skillRepository.findById(savedObj.getSkillId());
		assertEquals(skillObj.isPresent(), false);
				
	}
}
