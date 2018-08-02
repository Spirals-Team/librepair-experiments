package com.cts.iiht.fsd.skilltracker.associate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.iiht.fsd.skilltracker.SkillTrackerApplication;
import com.cts.iiht.fsd.skilltracker.associate.controller.AssociateController;
import com.cts.iiht.fsd.skilltracker.associate.service.AssociateService;
import com.cts.iiht.fsd.skilltracker.associate.to.AssociateSkillsTO;
import com.cts.iiht.fsd.skilltracker.associate.to.AssociateTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkillTrackerApplication.class)
@AutoConfigureMockMvc
public class AssociateMVCTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private AssociateTO associateTO;
	
	@Mock
	private AssociateService associateService;
	
	@InjectMocks
	private AssociateController associateController = new AssociateController();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(associateController).build();
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull(associateTO);
		assertNotNull("Associate service injection failed.", associateService);
	}
	
	private static String asJsonStringConvert(final Object obj){
		try{
			return new ObjectMapper().writeValueAsString(obj);
		}catch(Exception ex){
			throw new  RuntimeException(ex);
		}
	}
	
	@Test
	public void testGetAssociatesList() throws Exception{
		AssociateTO associateTO = new AssociateTO();
		List<AssociateTO> associatesList = new ArrayList<AssociateTO>();
		AssociateTO associateObj1 = new AssociateTO();
		associateObj1.setId(100L);
		associateObj1.setAssociateId(312256L);
		associateObj1.setAssociateName("JUDE");
		associateObj1.setEmail("jude@abc.com");
		associateObj1.setMobile(9876543210L);
		associateObj1.setStatus("blue");
		associateObj1.setSkills("HTML5, CSS3, JAVA");
		associatesList.add(associateObj1);
		AssociateTO associateObj2 = new AssociateTO();
		associateObj2.setId(101L);
		associateObj2.setAssociateId(123L);
		associateObj2.setAssociateName("MARSHAL");
		associateObj2.setEmail("marshal@abc.com");
		associateObj2.setMobile(8765432109L);
		associateObj2.setStatus("green");
		associateObj2.setSkills("HTML5, CSS3, DOT NET");
		associatesList.add(associateObj2);
		associateTO.setMalePercentage("100");
		associateTO.setFemalePercentage("0");
		associateTO.setFreshersPercentage("0");
		associateTO.setRatedAssociatesCount("2");
		associateTO.setMaleRatedPercentage("100");
		associateTO.setFemaleRatedPercentage("0");
		associateTO.setLevel1Percentage("0");
		associateTO.setLevel2Percentage("50");
		associateTO.setLevel3Percentage("50");
		associateTO.setAssociatesList(associatesList);
		associateTO.setAssociatesCount(((Integer)associatesList.size()).toString());
		
		when(associateService.getAssociatesList()).thenReturn(associateTO);
		
		mockMvc.perform(get("/skilltracker/associates/").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.associatesList", hasSize(2)));
		verify(associateService, times(1)).getAssociatesList();
	}
	
	@Test
	public void testGetAssociateById() throws Exception {
		AssociateTO associateTO = new AssociateTO();
		associateTO.setId(100L);
		associateTO.setAssociateId(312256L);
		associateTO.setAssociateName("JUDE");
		associateTO.setEmail("jude@abc.com");
		associateTO.setMobile(9876543210L);
		associateTO.setGender("MALE");
		associateTO.setLevel("LEVEL_1");
		associateTO.setStatus("green");
		associateTO.setRemark("Remarks on the associate");
		associateTO.setStrength("Strength of the associate");
		associateTO.setWeakness("Weakness of the associate");
		AssociateSkillsTO associateSkillsTO1 = new AssociateSkillsTO();
		associateSkillsTO1.setSkillId(1L);
		associateSkillsTO1.setSkillName("Skill 1");
		associateSkillsTO1.setSkillLevel(5L);
		AssociateSkillsTO associateSkillsTO2 = new AssociateSkillsTO();
		associateSkillsTO2.setSkillId(2L);
		associateSkillsTO2.setSkillName("Skill 2");
		associateSkillsTO2.setSkillLevel(0L);
		List<AssociateSkillsTO> associateSkillsList = new ArrayList<AssociateSkillsTO>();
		associateSkillsList.add(associateSkillsTO1);
		associateSkillsList.add(associateSkillsTO2);
		associateTO.setAssociateSkillsList(associateSkillsList);
		when(associateService.getAssociateById(100L)).thenReturn(associateTO);
		
		mockMvc.perform(get("/skilltracker/associates/100"))
				.andExpect(status().isOk());
		verify(associateService, times(1)).getAssociateById(100L);
	}
	
	@Test
	public void testSaveAssociate() throws Exception {
		AssociateTO associateTO = new AssociateTO();
		associateTO.setId(null);
		associateTO.setAssociateId(312256L);
		associateTO.setAssociateName("TARUN");
		associateTO.setEmail("tarun@abc.com");
		associateTO.setMobile(7654321098L);
		associateTO.setGender("MALE");
		associateTO.setLevel("LEVEL_1");
		associateTO.setStatus("green");
		associateTO.setRemark("Remarks on the associate");
		associateTO.setStrength("Strength of the associate");
		associateTO.setWeakness("Weakness of the associate");
		AssociateSkillsTO associateSkillsTO1 = new AssociateSkillsTO();
		associateSkillsTO1.setSkillId(1L);
		associateSkillsTO1.setSkillName("Skill 1");
		associateSkillsTO1.setSkillLevel(5L);
		AssociateSkillsTO associateSkillsTO2 = new AssociateSkillsTO();
		associateSkillsTO2.setSkillId(2L);
		associateSkillsTO2.setSkillName("Skill 2");
		associateSkillsTO2.setSkillLevel(0L);
		List<AssociateSkillsTO> associateSkillsList = new ArrayList<AssociateSkillsTO>();
		associateSkillsList.add(associateSkillsTO1);
		associateSkillsList.add(associateSkillsTO2);
		associateTO.setAssociateSkillsList(associateSkillsList);
		
		when(associateService.getAssociateById(110L)).thenReturn(null);
		when(associateService.saveAssociate(associateTO)).thenReturn(110L);
		
		mockMvc.perform(post("/skilltracker/associates/").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonStringConvert(associateTO)))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateAssociate() throws Exception {
		AssociateTO associateTO = new AssociateTO();
		associateTO.setId(110L);
		associateTO.setAssociateId(312256L);
		associateTO.setAssociateName("TARUN");
		associateTO.setEmail("tarun@abc.com");
		associateTO.setMobile(7654321098L);
		associateTO.setGender("MALE");
		associateTO.setLevel("LEVEL_1");
		associateTO.setStatus("green");
		associateTO.setRemark("Updated Remarks");
		associateTO.setStrength("Updated Strength");
		associateTO.setWeakness("Updated weakness");
		AssociateSkillsTO associateSkillsTO1 = new AssociateSkillsTO();
		associateSkillsTO1.setSkillId(1L);
		associateSkillsTO1.setSkillName("Skill 1");
		associateSkillsTO1.setSkillLevel(5L);
		AssociateSkillsTO associateSkillsTO2 = new AssociateSkillsTO();
		associateSkillsTO2.setSkillId(2L);
		associateSkillsTO2.setSkillName("Skill 2");
		associateSkillsTO2.setSkillLevel(10L);
		List<AssociateSkillsTO> associateSkillsList = new ArrayList<AssociateSkillsTO>();
		associateSkillsList.add(associateSkillsTO1);
		associateSkillsList.add(associateSkillsTO2);
		associateTO.setAssociateSkillsList(associateSkillsList);
		
		when(associateService.getAssociateById(110L)).thenReturn(associateTO);
		when(associateService.saveAssociate(associateTO)).thenReturn(110L);
		
		mockMvc.perform(put("/skilltracker/associates/").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonStringConvert(associateTO)))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteAssociate() throws Exception{
		AssociateTO associateTO = new AssociateTO();
		associateTO.setId(110L);
		associateTO.setAssociateId(312256L);
		associateTO.setAssociateName("TARUN");
		associateTO.setEmail("tarun@abc.com");
		associateTO.setMobile(7654321098L);
		associateTO.setGender("MALE");
		associateTO.setLevel("LEVEL_1");
		associateTO.setStatus("green");
		associateTO.setRemark("Updated Remarks");
		associateTO.setStrength("Updated Strength");
		associateTO.setWeakness("Updated weakness");
		AssociateSkillsTO associateSkillsTO1 = new AssociateSkillsTO();
		associateSkillsTO1.setSkillId(1L);
		associateSkillsTO1.setSkillName("Skill 1");
		associateSkillsTO1.setSkillLevel(5L);
		AssociateSkillsTO associateSkillsTO2 = new AssociateSkillsTO();
		associateSkillsTO2.setSkillId(2L);
		associateSkillsTO2.setSkillName("Skill 2");
		associateSkillsTO2.setSkillLevel(10L);
		List<AssociateSkillsTO> associateSkillsList = new ArrayList<AssociateSkillsTO>();
		associateSkillsList.add(associateSkillsTO1);
		associateSkillsList.add(associateSkillsTO2);
		associateTO.setAssociateSkillsList(associateSkillsList);
		
		when(associateService.getAssociateById(110L)).thenReturn(associateTO);
		when(associateService.deleteAssociate(110L)).thenReturn(true);
		when(associateService.getAssociateById(110L)).thenReturn(null);
		
		mockMvc.perform(delete("/skilltracker/associates/110").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testGetAssociateSkills() throws Exception {
		AssociateTO associateTO = new AssociateTO();
		AssociateSkillsTO associateSkillsTO1 = new AssociateSkillsTO();
		associateSkillsTO1.setSkillId(1L);
		associateSkillsTO1.setSkillName("Skill 1");
		associateSkillsTO1.setSkillLevel(0L);
		AssociateSkillsTO associateSkillsTO2 = new AssociateSkillsTO();
		associateSkillsTO2.setSkillId(2L);
		associateSkillsTO2.setSkillName("Skill 2");
		associateSkillsTO2.setSkillLevel(0L);
		List<AssociateSkillsTO> associateSkillsList = new ArrayList<AssociateSkillsTO>();
		associateSkillsList.add(associateSkillsTO1);
		associateSkillsList.add(associateSkillsTO2);
		associateTO.setAssociateSkillsList(associateSkillsList);
		
		when(associateService.getAssociateSkills()).thenReturn(associateTO);
		
		mockMvc.perform(get("/skilltracker/associates/skills/").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.associateSkillsList", hasSize(2)));
	}
}
