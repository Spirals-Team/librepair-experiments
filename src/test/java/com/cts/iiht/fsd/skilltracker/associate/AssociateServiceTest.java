package com.cts.iiht.fsd.skilltracker.associate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
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

import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateRepository;
import com.cts.iiht.fsd.skilltracker.associate.repository.AssociateSkillsRepository;
import com.cts.iiht.fsd.skilltracker.associate.service.impl.AssoicateServiceImpl;
import com.cts.iiht.fsd.skilltracker.associate.to.AssociateSkillsTO;
import com.cts.iiht.fsd.skilltracker.associate.to.AssociateTO;
import com.cts.iiht.fsd.skilltracker.framework.entity.Associate;
import com.cts.iiht.fsd.skilltracker.framework.entity.AssociateSkills;
import com.cts.iiht.fsd.skilltracker.framework.entity.Skill;
import com.cts.iiht.fsd.skilltracker.skill.repository.SkillRepository;

public class AssociateServiceTest {

	@Mock
	private SkillRepository skillRepository;
	
	@Mock
	private AssociateSkillsRepository associateSkillsRepository;
	
	@Mock
	private AssociateRepository associateRepository;
	
	@InjectMocks
	private AssoicateServiceImpl associateService;
	
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull("Associate service injection failed.", associateService);
	}
	
	@Test
	public void testGetAllAssociates() throws Exception {
		List<Object[]> associateResults = new ArrayList<Object[]>();
		Object[] associateObj1 = new Object[] {new BigInteger("1"),new BigInteger("123"),"JUDE","jude@abc.in",new BigInteger("9786543210"),true,false,false,"HTML5, CSS3, JAVA"};
		Object[] associateObj2 = new Object[] {new BigInteger("2"),new BigInteger("234"),"TARUN","jude@abc.in",new BigInteger("9786543210"),false,true,false,"HTML5, CSS3, JAVA"};
		Object[] associateObj3 = new Object[] {new BigInteger("3"),new BigInteger("345"),"MARSHAL","jude@abc.in",new BigInteger("9786543210"),false,false,true,"HTML5, CSS3, JAVA"};
		associateResults.add(associateObj1);
		associateResults.add(associateObj2);
		associateResults.add(associateObj3);
		when(associateRepository.getAssociatesDetailsList()).thenReturn(associateResults);
		List<Object[]> candidatesByGenderResult = new ArrayList<Object[]>();
		Object[] maleObj = new Object[] {"MALE",new BigInteger("3")};
		Object[] femaleObj = new Object[] {"FEMALE",new BigInteger("0")};
		candidatesByGenderResult.add(maleObj);
		candidatesByGenderResult.add(femaleObj);
		when(associateRepository.getCandidatesCountByGender()).thenReturn(candidatesByGenderResult);
		Object[] freshersResult = new Object[] {new BigInteger("1")};  
		when(associateRepository.getFreshersCount()).thenReturn(freshersResult);
		List<Object[]> ratedCandidatesByGenderResult = new ArrayList<Object[]>();
		Object[] ratedMaleObj = new Object[] {"MALE",new BigInteger("3")};
		Object[] ratedFemaleObj = new Object[] {"FEMALE",new BigInteger("0")};
		ratedCandidatesByGenderResult.add(ratedFemaleObj);
		ratedCandidatesByGenderResult.add(ratedMaleObj);
		when(associateRepository.getRatedCandidatesByGender()).thenReturn(ratedCandidatesByGenderResult);
		Object[] level1CandidatesResult =  new Object[] {new BigInteger("1")};
		when(associateRepository.getLevel1CandidatesCount()).thenReturn(level1CandidatesResult);
		Object[] level2CandidatesResult =  new Object[] {new BigInteger("1")};
		when(associateRepository.getLevel2CandidatesCount()).thenReturn(level2CandidatesResult);
		Object[] level3CandidatesResult =  new Object[] {new BigInteger("1")};
		when(associateRepository.getLevel3CandidatesCount()).thenReturn(level3CandidatesResult);
		AssociateTO associateTOObj = associateService.getAssociatesList();
		assertNotNull(associateTOObj);
	}
	
	@Test
	public void testGetAssociateById() throws Exception {
		Optional<Associate> associateOptionalObj = null;
		Associate associateObj = new Associate();
		associateObj.setId(100L);
		associateObj.setAssociateId(123L);
		associateObj.setAssociateName("JUDE");
		associateObj.setEmail("jude@abc.in");
		associateObj.setMobileNumber(9876543210L);
		associateObj.setLevel1(true);
		associateObj.setLevel2(false);
		associateObj.setLevel3(false);
		associateObj.setStatusBlue(true);
		associateObj.setStatusGreen(false);
		associateObj.setStatusRed(false);
		associateObj.setGender("MALE");
		associateObj.setRemark("Remark");
		associateObj.setStrength("Strength");
		associateObj.setWeakness("Weakness");
		associateOptionalObj = Optional.of(associateObj);
		when(associateRepository.findById(100L)).thenReturn(associateOptionalObj);
		List<Object[]> associateSkillsResult = new ArrayList<Object[]>();
		Object[] associateSkillObj =  new Object[] {new BigInteger("1"),"Skill 1", new BigInteger("10")};
		associateSkillsResult.add(associateSkillObj);
		when(associateRepository.getSkillsByAssociateId(100L)).thenReturn(associateSkillsResult);
		List<Skill> skillsList = new ArrayList<Skill>();
		Skill skill1 = new Skill();
		skill1.setSkillId(1L);
		skill1.setSkillName("Skill 1");
		skillsList.add(skill1);
		Skill skill2 = new Skill();
		skill2.setSkillId(2L);
		skill2.setSkillName("Skill 2");
		skillsList.add(skill2);
		when(skillRepository.findAll()).thenReturn(skillsList);
		AssociateTO associateTOObj = associateService.getAssociateById(100L);
		assertNotNull(associateTOObj);
	}
	
	@Test
	public void testSaveAssociate() throws Exception {
		Associate associateObj = new Associate();
		associateObj.setId(100L);
		associateObj.setAssociateId(123L);
		associateObj.setAssociateName("JUDE");
		associateObj.setEmail("jude@abc.in");
		associateObj.setMobileNumber(9876543210L);
		associateObj.setLevel1(true);
		associateObj.setLevel2(false);
		associateObj.setLevel3(false);
		associateObj.setStatusBlue(true);
		associateObj.setStatusGreen(false);
		associateObj.setStatusRed(false);
		associateObj.setGender("MALE");
		associateObj.setRemark("Remark");
		associateObj.setStrength("Strength");
		associateObj.setWeakness("Weakness");
		when(associateRepository.findById(100L)).thenReturn(Optional.empty());
		doNothing().when(associateSkillsRepository).deleteAll(associateObj.getAssociateSkillsSet());
		AssociateTO associateTOObj = new AssociateTO();
		associateTOObj.setId(null);
		associateTOObj.setAssociateId(123L);
		associateTOObj.setAssociateName("JUDE");
		associateTOObj.setEmail("jude@abc.in");
		associateTOObj.setMobile(9876543210L);
		associateTOObj.setLevel("LEVEL_1");
		associateTOObj.setStatus("blue");
		associateTOObj.setGender("Male");
		associateTOObj.setRemark("Remark");
		associateTOObj.setStrength("Strength");
		associateTOObj.setWeakness("Weakness");
		AssociateSkillsTO associateSkillTO = new AssociateSkillsTO();
		associateSkillTO.setSkillId(1L);
		associateSkillTO.setSkillLevel(10L);
		List<AssociateSkillsTO> associateSkillsTOList = new ArrayList<AssociateSkillsTO>();
		associateSkillsTOList.add(associateSkillTO);
		associateTOObj.setAssociateSkillsList(associateSkillsTOList);
		AssociateSkills associateSkill = new AssociateSkills();
		associateSkill.setId(1000L);
		associateSkill.setSkill(new Skill(1L));
		associateSkill.setAssociate(associateObj);
		associateSkill.setSkillLevel(10L);
		when(associateRepository.save(associateObj)).thenReturn(associateObj);
		when(associateSkillsRepository.save(associateSkill)).thenReturn(associateSkill);
		Long associateId = associateService.saveAssociate(associateTOObj);
		assertNotNull(associateId);
	}

	@Test
	public void testUpdateAssociate() throws Exception {
		Optional<Associate> associateOptionalObj = null;
		Associate associateObj = new Associate();
		associateObj.setId(100L);
		associateObj.setAssociateId(123L);
		associateObj.setAssociateName("JUDE");
		associateObj.setEmail("jude@abc.in");
		associateObj.setMobileNumber(9876543210L);
		associateObj.setLevel1(true);
		associateObj.setLevel2(false);
		associateObj.setLevel3(false);
		associateObj.setStatusBlue(true);
		associateObj.setStatusGreen(false);
		associateObj.setStatusRed(false);
		associateObj.setGender("MALE");
		associateObj.setRemark("Remark");
		associateObj.setStrength("Strength");
		associateObj.setWeakness("Weakness");
		AssociateSkills associateSkill = new AssociateSkills();
		associateSkill.setId(1000L);
		associateSkill.setSkill(new Skill(1L));
		associateSkill.setAssociate(associateObj);
		associateSkill.setSkillLevel(10L);
		Set<AssociateSkills> associateSkillSet = new HashSet<AssociateSkills>();
		associateSkillSet.add(associateSkill);
		associateObj.setAssociateSkillsSet(associateSkillSet);
		associateOptionalObj = Optional.of(associateObj);
		when(associateRepository.findById(100L)).thenReturn(associateOptionalObj);
		doNothing().when(associateSkillsRepository).deleteAll(associateObj.getAssociateSkillsSet());
		AssociateTO associateTOObj = new AssociateTO();
		associateTOObj.setId(100L);
		associateTOObj.setAssociateId(123L);
		associateTOObj.setAssociateName("JUDE");
		associateTOObj.setEmail("jude@abc.in");
		associateTOObj.setMobile(9876543210L);
		associateTOObj.setLevel("LEVEL_1");
		associateTOObj.setStatus("blue");
		associateTOObj.setGender("Male");
		associateTOObj.setRemark("Remark");
		associateTOObj.setStrength("Strength");
		associateTOObj.setWeakness("Weakness");
		AssociateSkillsTO associateSkillTO = new AssociateSkillsTO();
		associateSkillTO.setSkillId(1L);
		associateSkillTO.setSkillLevel(10L);
		List<AssociateSkillsTO> associateSkillsTOList = new ArrayList<AssociateSkillsTO>();
		associateSkillsTOList.add(associateSkillTO);
		associateTOObj.setAssociateSkillsList(associateSkillsTOList);
		when(associateRepository.save(associateObj)).thenReturn(associateObj);
		when(associateSkillsRepository.save(associateSkill)).thenReturn(associateSkill);
		Long associateId = associateService.saveAssociate(associateTOObj);
		assertNotNull(associateId);
	}
	
	@Test
	public void testDeleteAssocaite() throws Exception {
		Optional<Associate> associateOptionalObj = null;
		Associate associateObj = new Associate();
		associateObj.setId(100L);
		associateObj.setAssociateId(123L);
		associateObj.setAssociateName("JUDE");
		associateObj.setEmail("jude@abc.in");
		associateObj.setMobileNumber(9876543210L);
		associateObj.setLevel1(true);
		associateObj.setLevel2(false);
		associateObj.setLevel3(false);
		associateObj.setStatusBlue(true);
		associateObj.setStatusGreen(false);
		associateObj.setStatusRed(false);
		associateObj.setGender("MALE");
		associateObj.setRemark("Remark");
		associateObj.setStrength("Strength");
		associateObj.setWeakness("Weakness");
		AssociateSkills associateSkill = new AssociateSkills();
		associateSkill.setId(1000L);
		associateSkill.setSkill(new Skill(1L));
		associateSkill.setAssociate(associateObj);
		associateSkill.setSkillLevel(10L);
		Set<AssociateSkills> associateSkillSet = new HashSet<AssociateSkills>();
		associateSkillSet.add(associateSkill);
		associateObj.setAssociateSkillsSet(associateSkillSet);
		associateOptionalObj = Optional.of(associateObj);
		when(associateRepository.findById(100L)).thenReturn(associateOptionalObj);
		doNothing().when(associateSkillsRepository).deleteAll(associateObj.getAssociateSkillsSet());
		doNothing().when(associateRepository).delete(associateObj);
		boolean result = associateService.deleteAssociate(100L);
		assertNotNull(result);
	}
	
	@Test
	public void testGetAssociateSkills() throws Exception {
		List<Skill> skillsList = new ArrayList<Skill>();
		Skill skill1 = new Skill();
		skill1.setSkillId(1L);
		skill1.setSkillName("Skill 1");
		skillsList.add(skill1);
		when(skillRepository.findAll()).thenReturn(skillsList);
		AssociateTO associateTO = associateService.getAssociateSkills();
		assertNotNull(associateTO);
	}
	
}
