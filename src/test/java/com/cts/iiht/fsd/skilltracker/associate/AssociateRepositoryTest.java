package com.cts.iiht.fsd.skilltracker.associate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
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
import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateRepository;
import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateSkillsRepository;
import com.cts.iiht.fsd.skilltracker.framework.entity.Associate;
import com.cts.iiht.fsd.skilltracker.framework.entity.AssociateSkills;
import com.cts.iiht.fsd.skilltracker.framework.entity.Skill;
import com.cts.iiht.fsd.skilltracker.skill.repository.SkillRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@SpringBootTest(classes = SkillTrackerApplication.class)
@Transactional
public class AssociateRepositoryTest {
	
	@Autowired
	private transient AssociateRepository associateRepository;
	
	@Autowired
	private transient AssociateSkillsRepository associateSkillsRepository;
	
	@Autowired
	private transient SkillRepository skillRepository;
	
	public void setRepo(final AssociateRepository repository) {
		this.associateRepository = repository;
	}
	
	public void setRepo(final AssociateSkillsRepository repository) {
		this.associateSkillsRepository = repository;
	}
	
	public void setRepo(final SkillRepository repository) {
		this.skillRepository = repository;
	}
	
	@Test
	public void testGetAllAssociates() throws Exception{
		Associate associate = new Associate();
		associate.setId(null);
		associate.setAssociateId(1000L);
		associate.setAssociateName("TARUN");
		associate.setEmail("tarun@abc.com");
		associate.setMobileNumber(7654321098L);
		associate.setGender("MALE");
		associate.setLevel1(true);
		associate.setLevel2(false);
		associate.setLevel3(false);
		associate.setStatusBlue(true);
		associate.setStatusGreen(false);
		associate.setStatusRed(false);
		associate.setRemark("Remarks on the associate");
		associate.setStrength("Strength of the associate");
		associate.setWeakness("Weakness of the associate");
		Associate savedAssociateObj = associateRepository.save(associate);
		Skill skill1 = new Skill();
		skill1.setSkillId(null);
		skill1.setSkillName("Skill 1");
		Skill savedSkillObj1 = skillRepository.save(skill1);
		Skill skill2 = new Skill();
		skill2.setSkillId(null);
		skill2.setSkillName("Skill 1");
		Skill savedSkillObj2 = skillRepository.save(skill2);
		AssociateSkills associateSkills1 = new AssociateSkills();
		associateSkills1.setId(null);
		associateSkills1.setSkill(savedSkillObj1);
		associateSkills1.setAssociate(savedAssociateObj);
		associateSkills1.setSkillLevel(5L);
		associateSkillsRepository.save(associateSkills1);
		AssociateSkills associateSkills2 = new AssociateSkills();
		associateSkills2.setId(null);
		associateSkills2.setSkill(savedSkillObj2);
		associateSkills2.setAssociate(savedAssociateObj);
		associateSkills2.setSkillLevel(10L);
		associateSkillsRepository.save(associateSkills2);
		
		final List<Object[]> associatesObjList = associateRepository.getAssociatesDetailsList();
		assertNotNull(associatesObjList);
	}
	
	@Test
	public void testGetAssociateById() throws Exception {
		Associate associate = new Associate();
		associate.setId(null);
		associate.setAssociateId(1001L);
		associate.setAssociateName("TARUN");
		associate.setEmail("tarun@abc.com");
		associate.setMobileNumber(7654321098L);
		associate.setGender("MALE");
		associate.setLevel1(true);
		associate.setLevel2(false);
		associate.setLevel3(false);
		associate.setStatusBlue(true);
		associate.setStatusGreen(false);
		associate.setStatusRed(false);
		associate.setRemark("Remarks on the associate");
		associate.setStrength("Strength of the associate");
		associate.setWeakness("Weakness of the associate");
		Associate savedAssociateObj = associateRepository.save(associate);
		Skill skill1 = new Skill();
		skill1.setSkillId(null);
		skill1.setSkillName("Skill 1");
		Skill savedSkillObj1 = skillRepository.save(skill1);
		AssociateSkills associateSkills1 = new AssociateSkills();
		associateSkills1.setId(null);
		associateSkills1.setSkill(savedSkillObj1);
		associateSkills1.setAssociate(savedAssociateObj);
		associateSkills1.setSkillLevel(5L);
		associateSkillsRepository.save(associateSkills1);
		final Optional<Associate> associateObj = associateRepository.findById(savedAssociateObj.getId());
		assertNotNull(associateObj);
		assertEquals(associateObj.get().getAssociateName(), "TARUN");
	}
	
	@Test
	public void testSaveAssociate() throws Exception {
		Associate associate = new Associate();
		associate.setId(null);
		associate.setAssociateId(1003L);
		associate.setAssociateName("TARUN");
		associate.setEmail("tarun@abc.com");
		associate.setMobileNumber(7654321098L);
		associate.setGender("MALE");
		associate.setLevel1(true);
		associate.setLevel2(false);
		associate.setLevel3(false);
		associate.setStatusBlue(true);
		associate.setStatusGreen(false);
		associate.setStatusRed(false);
		associate.setRemark("Remarks on the associate");
		associate.setStrength("Strength of the associate");
		associate.setWeakness("Weakness of the associate");
		Associate savedAssociateObj = associateRepository.save(associate);
		Skill skill1 = new Skill();
		skill1.setSkillId(null);
		skill1.setSkillName("Skill 1");
		Skill savedSkillObj1 = skillRepository.save(skill1);
		AssociateSkills associateSkills1 = new AssociateSkills();
		associateSkills1.setId(null);
		associateSkills1.setSkill(savedSkillObj1);
		associateSkills1.setAssociate(savedAssociateObj);
		associateSkills1.setSkillLevel(5L);
		AssociateSkills associateSkillsSavedObj = associateSkillsRepository.save(associateSkills1);
		assertNotNull(savedAssociateObj);
		assertNotNull(associateSkillsSavedObj);
		assertNotNull(associateSkillsSavedObj.getId());
	}
	
	@Test
	public void testUpdateSkill() throws Exception {
		Associate associate = new Associate();
		associate.setId(null);
		associate.setAssociateId(1004L);
		associate.setAssociateName("TARUN");
		associate.setEmail("tarun@abc.com");
		associate.setMobileNumber(7654321098L);
		associate.setGender("MALE");
		associate.setLevel1(true);
		associate.setLevel2(false);
		associate.setLevel3(false);
		associate.setStatusBlue(true);
		associate.setStatusGreen(false);
		associate.setStatusRed(false);
		associate.setRemark("Remarks on the associate");
		associate.setStrength("Strength of the associate");
		associate.setWeakness("Weakness of the associate");
		Associate savedAssociateObj = associateRepository.save(associate);
		Skill skill1 = new Skill();
		skill1.setSkillId(null);
		skill1.setSkillName("Skill 1");
		Skill savedSkillObj1 = skillRepository.save(skill1);
		AssociateSkills associateSkills1 = new AssociateSkills();
		associateSkills1.setId(null);
		associateSkills1.setSkill(savedSkillObj1);
		associateSkills1.setAssociate(savedAssociateObj);
		associateSkills1.setSkillLevel(5L);
		associateSkillsRepository.save(associateSkills1);
		
		Associate updateAssociate = new Associate();
		updateAssociate.setId(savedAssociateObj.getId());
		updateAssociate.setAssociateId(1005L);
		updateAssociate.setAssociateName("JUDE TARUN MARSHAL");
		updateAssociate.setEmail("tarun@abc.com");
		updateAssociate.setMobileNumber(7654321098L);
		updateAssociate.setGender("MALE");
		updateAssociate.setLevel1(true);
		updateAssociate.setLevel2(false);
		updateAssociate.setLevel3(false);
		updateAssociate.setStatusBlue(true);
		updateAssociate.setStatusGreen(false);
		updateAssociate.setStatusRed(false);
		updateAssociate.setRemark("Remarks on the associate");
		updateAssociate.setStrength("Strength of the associate");
		updateAssociate.setWeakness("Weakness of the associate");
		Associate updatedAssociateObj = associateRepository.save(updateAssociate);
		associateSkillsRepository.deleteAll(savedAssociateObj.getAssociateSkillsSet());
		AssociateSkills updateAssociateSkills = new AssociateSkills();
		updateAssociateSkills.setId(null);
		updateAssociateSkills.setSkill(savedSkillObj1);
		updateAssociateSkills.setAssociate(updatedAssociateObj);
		updateAssociateSkills.setSkillLevel(10L);
		associateSkillsRepository.save(updateAssociateSkills);
		
		assertNotNull(updatedAssociateObj);
		assertNotNull(updatedAssociateObj.getId());
		assertEquals(updatedAssociateObj.getAssociateName(), "JUDE TARUN MARSHAL");
	}
	
	@Test
	public void testDeleteAssociate() throws Exception{
		Associate associate = new Associate();
		associate.setId(null);
		associate.setAssociateId(1006L);
		associate.setAssociateName("TARUN");
		associate.setEmail("tarun@abc.com");
		associate.setMobileNumber(7654321098L);
		associate.setGender("MALE");
		associate.setLevel1(true);
		associate.setLevel2(false);
		associate.setLevel3(false);
		associate.setStatusBlue(true);
		associate.setStatusGreen(false);
		associate.setStatusRed(false);
		associate.setRemark("Remarks on the associate");
		associate.setStrength("Strength of the associate");
		associate.setWeakness("Weakness of the associate");
		Associate savedAssociateObj = associateRepository.save(associate);
		Skill skill1 = new Skill();
		skill1.setSkillId(null);
		skill1.setSkillName("Skill 1");
		Skill savedSkillObj1 = skillRepository.save(skill1);
		AssociateSkills associateSkills1 = new AssociateSkills();
		associateSkills1.setId(null);
		associateSkills1.setSkill(savedSkillObj1);
		associateSkills1.setAssociate(savedAssociateObj);
		associateSkills1.setSkillLevel(5L);
		associateSkillsRepository.save(associateSkills1);

		Associate associateToDelete = null;
		Optional<Associate> associateOptionalObj = associateRepository.findById(savedAssociateObj.getId());
		if(associateOptionalObj.isPresent()) {
			associateToDelete = associateOptionalObj.get();
		}
		assertNotNull(associateToDelete);
		associateRepository.delete(associateToDelete);
		final Optional<Associate> associateObj = associateRepository.findById(savedAssociateObj.getId());
		assertEquals(associateObj.isPresent(), false);
	}
	
	public void testGetCandidatesCountByGender() throws Exception {
		Associate maleAssociate = new Associate();
		maleAssociate.setId(null);
		maleAssociate.setAssociateId(1007L);
		maleAssociate.setAssociateName("TARUN");
		maleAssociate.setEmail("tarun@abc.com");
		maleAssociate.setMobileNumber(7654321098L);
		maleAssociate.setGender("MALE");
		maleAssociate.setLevel1(true);
		maleAssociate.setLevel2(false);
		maleAssociate.setLevel3(false);
		maleAssociate.setStatusBlue(true);
		maleAssociate.setStatusGreen(false);
		maleAssociate.setStatusRed(false);
		maleAssociate.setRemark("Remarks on the associate");
		maleAssociate.setStrength("Strength of the associate");
		maleAssociate.setWeakness("Weakness of the associate");
		associateRepository.save(maleAssociate);
		
		Associate femaleAssociate = new Associate();
		femaleAssociate.setId(null);
		femaleAssociate.setAssociateId(1008L);
		femaleAssociate.setAssociateName("SHEREEN");
		femaleAssociate.setEmail("shereen@abc.com");
		femaleAssociate.setMobileNumber(7654321098L);
		femaleAssociate.setGender("FEMALE");
		femaleAssociate.setLevel1(true);
		femaleAssociate.setLevel2(false);
		femaleAssociate.setLevel3(false);
		femaleAssociate.setStatusBlue(true);
		femaleAssociate.setStatusGreen(false);
		femaleAssociate.setStatusRed(false);
		femaleAssociate.setRemark("Remarks on the associate");
		femaleAssociate.setStrength("Strength of the associate");
		femaleAssociate.setWeakness("Weakness of the associate");
		associateRepository.save(femaleAssociate);
		
		final List<Object[]> resultList = associateRepository.getCandidatesCountByGender();
		assertNotNull(resultList);
		assertEquals(resultList.size(), 2);
	}
	
	@Test
	public void testGetFreshersCount() throws Exception {
		Associate fresherAssociate = new Associate();
		fresherAssociate.setId(null);
		fresherAssociate.setAssociateId(1009L);
		fresherAssociate.setAssociateName("TARUN");
		fresherAssociate.setEmail("tarun@abc.com");
		fresherAssociate.setMobileNumber(7654321098L);
		fresherAssociate.setGender("MALE");
		fresherAssociate.setLevel1(true);
		fresherAssociate.setLevel2(false);
		fresherAssociate.setLevel3(false);
		fresherAssociate.setStatusBlue(true);
		fresherAssociate.setStatusGreen(false);
		fresherAssociate.setStatusRed(false);
		fresherAssociate.setRemark("Remarks on the associate");
		fresherAssociate.setStrength("Strength of the associate");
		fresherAssociate.setWeakness("Weakness of the associate");
		associateRepository.save(fresherAssociate);
		
		final Object[] resultList = associateRepository.getFreshersCount();
		assertNotNull(resultList);
	}	
	
	@Test
	public void testGetRatedCandidatesByGender() throws Exception{
		Skill skill1 = new Skill();
		skill1.setSkillId(null);
		skill1.setSkillName("Skill 1");
		Skill savedSkillObj1 = skillRepository.save(skill1);
		
		Associate maleAssociate = new Associate();
		maleAssociate.setId(null);
		maleAssociate.setAssociateId(1010L);
		maleAssociate.setAssociateName("TARUN");
		maleAssociate.setEmail("tarun@abc.com");
		maleAssociate.setMobileNumber(7654321098L);
		maleAssociate.setGender("MALE");
		maleAssociate.setLevel1(true);
		maleAssociate.setLevel2(false);
		maleAssociate.setLevel3(false);
		maleAssociate.setStatusBlue(true);
		maleAssociate.setStatusGreen(false);
		maleAssociate.setStatusRed(false);
		maleAssociate.setRemark("Remarks on the associate");
		maleAssociate.setStrength("Strength of the associate");
		maleAssociate.setWeakness("Weakness of the associate");
		Associate savedAssociateObj = associateRepository.save(maleAssociate);
		
		AssociateSkills associateSkills1 = new AssociateSkills();
		associateSkills1.setId(null);
		associateSkills1.setSkill(savedSkillObj1);
		associateSkills1.setAssociate(savedAssociateObj);
		associateSkills1.setSkillLevel(5L);
		associateSkillsRepository.save(associateSkills1);
		
		Associate femaleAssociate = new Associate();
		femaleAssociate.setId(null);
		femaleAssociate.setAssociateId(1011L);
		femaleAssociate.setAssociateName("SHEREEN");
		femaleAssociate.setEmail("shereen@abc.com");
		femaleAssociate.setMobileNumber(7654321098L);
		femaleAssociate.setGender("FEMALE");
		femaleAssociate.setLevel1(true);
		femaleAssociate.setLevel2(false);
		femaleAssociate.setLevel3(false);
		femaleAssociate.setStatusBlue(true);
		femaleAssociate.setStatusGreen(false);
		femaleAssociate.setStatusRed(false);
		femaleAssociate.setRemark("Remarks on the associate");
		femaleAssociate.setStrength("Strength of the associate");
		femaleAssociate.setWeakness("Weakness of the associate");
		Associate savedFemaleAssociateObj = associateRepository.save(femaleAssociate);
		AssociateSkills associateSkills2 = new AssociateSkills();
		associateSkills2.setId(null);
		associateSkills2.setSkill(savedSkillObj1);
		associateSkills2.setAssociate(savedFemaleAssociateObj);
		associateSkills2.setSkillLevel(15L);
		associateSkillsRepository.save(associateSkills2);
		
		final List<Object[]> resultList = associateRepository.getRatedCandidatesByGender();
		assertNotNull(resultList);
		assertEquals(resultList.size(), 2);
	}
	
	@Test
	public void testGetLevel1AssociatesCount() throws Exception {
		Associate fresherAssociate = new Associate();
		fresherAssociate.setId(null);
		fresherAssociate.setAssociateId(1012L);
		fresherAssociate.setAssociateName("TARUN");
		fresherAssociate.setEmail("tarun@abc.com");
		fresherAssociate.setMobileNumber(7654321098L);
		fresherAssociate.setGender("MALE");
		fresherAssociate.setLevel1(true);
		fresherAssociate.setLevel2(false);
		fresherAssociate.setLevel3(false);
		fresherAssociate.setStatusBlue(true);
		fresherAssociate.setStatusGreen(false);
		fresherAssociate.setStatusRed(false);
		fresherAssociate.setRemark("Remarks on the associate");
		fresherAssociate.setStrength("Strength of the associate");
		fresherAssociate.setWeakness("Weakness of the associate");
		associateRepository.save(fresherAssociate);
		
		final Object[] resultList = associateRepository.getLevel1CandidatesCount();
		assertNotNull(resultList);
	}	
	
	@Test
	public void testGetLevel2AssociatesCount() throws Exception {
		Associate fresherAssociate = new Associate();
		fresherAssociate.setId(null);
		fresherAssociate.setAssociateId(1013L);
		fresherAssociate.setAssociateName("TARUN");
		fresherAssociate.setEmail("tarun@abc.com");
		fresherAssociate.setMobileNumber(7654321098L);
		fresherAssociate.setGender("MALE");
		fresherAssociate.setLevel1(false);
		fresherAssociate.setLevel2(true);
		fresherAssociate.setLevel3(false);
		fresherAssociate.setStatusBlue(true);
		fresherAssociate.setStatusGreen(false);
		fresherAssociate.setStatusRed(false);
		fresherAssociate.setRemark("Remarks on the associate");
		fresherAssociate.setStrength("Strength of the associate");
		fresherAssociate.setWeakness("Weakness of the associate");
		associateRepository.save(fresherAssociate);
		
		final Object[] resultList = associateRepository.getLevel2CandidatesCount();
		assertNotNull(resultList);
	}	
	
	@Test
	public void testGetLevel3AssociatesCount() throws Exception {
		Associate fresherAssociate = new Associate();
		fresherAssociate.setId(null);
		fresherAssociate.setAssociateId(1014L);
		fresherAssociate.setAssociateName("TARUN");
		fresherAssociate.setEmail("tarun@abc.com");
		fresherAssociate.setMobileNumber(7654321098L);
		fresherAssociate.setGender("MALE");
		fresherAssociate.setLevel1(false);
		fresherAssociate.setLevel2(false);
		fresherAssociate.setLevel3(true);
		fresherAssociate.setStatusBlue(true);
		fresherAssociate.setStatusGreen(false);
		fresherAssociate.setStatusRed(false);
		fresherAssociate.setRemark("Remarks on the associate");
		fresherAssociate.setStrength("Strength of the associate");
		fresherAssociate.setWeakness("Weakness of the associate");
		associateRepository.save(fresherAssociate);
		
		final Object[] resultList = associateRepository.getLevel3CandidatesCount();
		assertNotNull(resultList);
	}	
	
	@Test
	public void testGetSkillsByAssociateId() throws Exception {
		Skill skill1 = new Skill();
		skill1.setSkillId(null);
		skill1.setSkillName("Skill 1");
		Skill savedSkillObj1 = skillRepository.save(skill1);
		Skill skill2 = new Skill();
		skill2.setSkillId(null);
		skill2.setSkillName("Skill 2");
		Skill savedSkillObj2 = skillRepository.save(skill2);
		
		Associate maleAssociate = new Associate();
		maleAssociate.setId(null);
		maleAssociate.setAssociateId(1015L);
		maleAssociate.setAssociateName("TARUN");
		maleAssociate.setEmail("tarun@abc.com");
		maleAssociate.setMobileNumber(7654321098L);
		maleAssociate.setGender("MALE");
		maleAssociate.setLevel1(true);
		maleAssociate.setLevel2(false);
		maleAssociate.setLevel3(false);
		maleAssociate.setStatusBlue(true);
		maleAssociate.setStatusGreen(false);
		maleAssociate.setStatusRed(false);
		maleAssociate.setRemark("Remarks on the associate");
		maleAssociate.setStrength("Strength of the associate");
		maleAssociate.setWeakness("Weakness of the associate");
		Associate savedAssociateObj = associateRepository.save(maleAssociate);
		
		AssociateSkills associateSkills1 = new AssociateSkills();
		associateSkills1.setId(null);
		associateSkills1.setSkill(savedSkillObj1);
		associateSkills1.setAssociate(savedAssociateObj);
		associateSkills1.setSkillLevel(5L);
		associateSkillsRepository.save(associateSkills1);
		
		AssociateSkills associateSkills2 = new AssociateSkills();
		associateSkills2.setId(null);
		associateSkills2.setSkill(savedSkillObj2);
		associateSkills2.setAssociate(savedAssociateObj);
		associateSkills2.setSkillLevel(15L);
		associateSkillsRepository.save(associateSkills2);
		
		final List<Object[]> resultList = associateRepository.getSkillsByAssociateId(savedAssociateObj.getId());
		assertNotNull(resultList);
	}
}
