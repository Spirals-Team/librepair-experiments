package com.rexam;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	@WithMockUser()
	public void testShowAllUnits() throws Exception {
		int size = 1000;
		String code = "ENSMABU27";

		 this.mockMvc.perform(get("/rexam/showTeachingUnits")).andDo(print()).andExpect(status().isOk())
		 .andExpect(view().name("teachingUnits"))
		.andExpect(model().attribute("tuList", hasSize(size))
				 );
		 // Inscription a une UE, ce lien redirige vers la liste des ues
		 this.mockMvc.perform(get("/rexam/registration").param("code", code)).andDo(print()).andExpect(status().is3xxRedirection())
		 .andExpect(redirectedUrl("/rexam/showTeachingUnits"))
		 .andExpect(model().attribute("tuList", hasSize(size-1)));
		 
	}
	
	
	/*
	 * Si on n'est pas connecté on ne peut pas acceder à la page 
	 */
	@Test
	public void testShowAllUnitsWithoutUser() throws Exception {
		 this.mockMvc.perform(get("/rexam/showTeachingUnits")).andDo(print()).andExpect(status().is3xxRedirection()
				 );
	}
	
	@Test
	@WithMockUser(authorities = { "student"})
	public void testShowExams() throws Exception {
		String code = "ENSMABU27";
		 this.mockMvc.perform(get("/rexam/showExams").param("code", code)).andDo(print()).andExpect(status().isOk())
		  .andExpect(forwardedUrl("/WEB-INF/view/exams.jsp"))
		  .andExpect(view().name("exams"))
		  .andExpect(model().attribute("teachingUnit", hasProperty("code", is(code)))
				 );
	}
	
	@Test
	public void testShowExamWithoutUser() throws Exception {
		 this.mockMvc.perform(get("/rexam/showExam")).andDo(print()).andExpect(status().is3xxRedirection()
				 );
	}

}
