package com.rexam;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.core.IsNull;
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
public class SearchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	/*
	 * Recherche le mot "base" qui retourne une liste de 15 UE
	 */
	@Test
	@WithMockUser(authorities = { "student"})
	public void testSearch() throws Exception {
		String searchTerm = "base";
		int size = 15; // nombre d'ue qui contiennent le mot "base"
		this.mockMvc.perform(get("/rexam/search").param("searchTerm", searchTerm)).andDo(print()).andExpect(status().isOk())
		.andExpect(forwardedUrl("/WEB-INF/view/search.jsp"))
		.andExpect(view().name("search"))
		.andExpect(model().attribute("searchTerm",is(searchTerm)))
		.andExpect(model().attributeExists("searchResults"))
		.andExpect(model().attribute("searchResults", hasSize(size))
				);
	}
	
	/*
	 * Recherche le mot "aaa" qui retourne une liste vide
	 */
	@Test
	@WithMockUser(authorities = { "student"})
	public void testSearchWithNoResults() throws Exception {
		String searchTerm = "aaa";
		int size = 0; 
		this.mockMvc.perform(get("/rexam/search").param("searchTerm", searchTerm)).andDo(print()).andExpect(status().isOk())
		.andExpect(forwardedUrl("/WEB-INF/view/search.jsp"))
		.andExpect(view().name("search"))
		.andExpect(model().attribute("searchTerm",is(searchTerm)))
		.andExpect(model().attributeExists("searchResults"))
		.andExpect(model().attribute("searchResults", hasSize(size))
				);
	}
	
	/*
	 * Recherche le mot "   "
	 */
	@Test
	@WithMockUser(authorities = { "student"})
	public void testSearchWhitespaces() throws Exception {
		String searchTerm = "     ";
		this.mockMvc.perform(get("/rexam/search").param("searchTerm", searchTerm)).andDo(print()).andExpect(status().isOk())
		.andExpect(forwardedUrl("/WEB-INF/view/search.jsp"))
		.andExpect(view().name("search"))
		.andExpect(model().attribute("searchTerm",is(searchTerm)))
		.andExpect(model().attribute("searchResults", is(IsNull.nullValue()))
				);
	}

	
}
