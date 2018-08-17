package com.tommasopuccetti.server;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = WebController.class)
@Import(WebSecurityConfig.class)
public class WebControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy springSecurityFilter;
	protected MockMvc mockMvc;

	@MockBean
	private IGridService gridService;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilter).build();
	}
	
	
	@Test
	public void testLogin401() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().is(401));
}
	
	@Test
	public void testLoginOk() throws Exception {
		mockMvc.perform(get("/").with(httpBasic("user", "password")))
			.andExpect(status().isOk());
}
	
	

}
