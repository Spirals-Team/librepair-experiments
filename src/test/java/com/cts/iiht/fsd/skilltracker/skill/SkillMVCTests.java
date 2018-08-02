package com.cts.iiht.fsd.skilltracker.skill;

import static org.hamcrest.CoreMatchers.is;
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

import java.util.Arrays;

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
import com.cts.iiht.fsd.skilltracker.skill.controller.SkillController;
import com.cts.iiht.fsd.skilltracker.skill.service.SkillService;
import com.cts.iiht.fsd.skilltracker.skill.to.SkillTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SkillTrackerApplication.class)
@AutoConfigureMockMvc
public class SkillMVCTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private SkillTO skillTO;
	
	@Mock
	private SkillService skillService;
	
	@InjectMocks
	private SkillController skillController = new SkillController();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(skillController).build();
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull(skillTO);
		assertNotNull("Skill service injection failed.", skillService);
	}
	
	private static String asJsonStringConvert(final Object obj){
		try{
			return new ObjectMapper().writeValueAsString(obj);
		}catch(Exception ex){
			throw new  RuntimeException(ex);
		}
	}
	
	@Test
	public void testGetAllSkills() throws Exception{
		SkillTO skillTO1 = new SkillTO();
		skillTO1.setSkillId(1L);
		skillTO1.setSkillName("Skill 1");
		SkillTO skillTO2 = new SkillTO();
		skillTO2.setSkillId(2L);
		skillTO2.setSkillName("Skill 2");
		when(skillService.getSkillsList()).thenReturn(Arrays.asList(skillTO1,skillTO2));
		
		mockMvc.perform(get("/skilltracker/skills/").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].skillName", is("Skill 1")))
				.andExpect(jsonPath("$[1].skillName", is("Skill 2")));
		verify(skillService, times(1)).getSkillsList();
	}
	
	@Test
	public void testGetSkillById() throws Exception {
		SkillTO skillTO1 = new SkillTO();
		skillTO1.setSkillId(1L);
		skillTO1.setSkillName("Skill 1");
		when(skillService.getSkillById(1L)).thenReturn(skillTO1);
		
		mockMvc.perform(get("/skilltracker/skills/1"))
				.andExpect(status().isOk());
		verify(skillService, times(1)).getSkillById(1L);
	}
	
	@Test
	public void testSaveSkill() throws Exception {
		SkillTO skillTO1 = new SkillTO();
		skillTO1.setSkillId(null);
		skillTO1.setSkillName("New Skill");
		
		when(skillService.getSkillById(10L)).thenReturn(null);
		when(skillService.saveSkill(skillTO1)).thenReturn(10L);
		
		mockMvc.perform(post("/skilltracker/skills/").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonStringConvert(skillTO1)))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateSkill() throws Exception {
		SkillTO skillTO1 = new SkillTO();
		skillTO1.setSkillId(10L);
		skillTO1.setSkillName("Updated Skill name");
		
		when(skillService.getSkillById(11L)).thenReturn(skillTO1);
		when(skillService.saveSkill(skillTO1)).thenReturn(10L);
		
		mockMvc.perform(put("/skilltracker/skills/").contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonStringConvert(skillTO1)))
					.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteSkill() throws Exception{
		SkillTO skillTO1 = new SkillTO();
		skillTO1.setSkillId(10L);
		skillTO1.setSkillName("Updated Skill name");
		
		when(skillService.getSkillById(10L)).thenReturn(skillTO1);
		when(skillService.deleteSkill(10L)).thenReturn(true);
		when(skillService.getSkillById(10L)).thenReturn(null);
		
		mockMvc.perform(delete("/skilltracker/skills/10").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
				
	}
}
