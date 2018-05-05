package uo.asw.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;

import org.apache.commons.collections.map.MultiValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import uo.asw.InciManagerE3aApplication;
import uo.asw.inciManager.service.AgentService;

@SpringBootTest(classes = InciManagerE3aApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class IncidenciasControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private AgentService agentService;

	private MockMvc mockMvc;
	private MockHttpSession session;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		session = new MockHttpSession();
	}
	
	@Test
	public void testIncidenceListRedirectToLogin() throws Exception {
		MockHttpServletRequestBuilder request = get("/incidencia/list").session(session);
		mockMvc.perform(request).andExpect(view().name("redirect:/login"));
	}
	
	@Test
	public void testIncidenceList() throws Exception {
		agentService.setIdConnected("Id1");
		MockHttpServletRequestBuilder request = get("/incidencia/list").session(session);
		mockMvc.perform(request).andExpect(view().name("incidencia/list"));
	}
	
	@Test
	public void testIncidenceCreateRedirectToLogin() throws Exception {
		MockHttpServletRequestBuilder request = get("/incidencia/create").session(session);
		mockMvc.perform(request).andExpect(view().name("redirect:/login"));
	}
	
	@Test
	public void testIncidenceCreateGET() throws Exception {
		agentService.setIdConnected("Id1");
		MockHttpServletRequestBuilder request = get("/incidencia/create").session(session);
		mockMvc.perform(request).andExpect(view().name("incidencia/create"));
	}
	
//	@Test
//	public void testIncidenceCreatePOST() throws Exception {
//		agentService.setIdConnected("Id1");
//		String json = "{\r\n" + 
//				"    \"_id\": {\r\n" + 
//				"        \"$oid\": \"5abba67c13c3a65724282ed5\"\r\n" + 
//				"    },\r\n" + 
//				"    \"_class\": \"uo.asw.model.Agente\",\r\n" + 
//				"    \"nombre\": \"Agente1\",\r\n" + 
//				"    \"contrasena\": \"123456\",\r\n" + 
//				"    \"kind\": \"person\",\r\n" + 
//				"    \"identificador\": \"Id1\",\r\n" + 
//				"    \"latitud\": \"43.355138\",\r\n" + 
//				"    \"longitud\": \"-5.851234\",\r\n" + 
//				"    \"email\": \"agente1@prueba.es\",\r\n" + 
//				"    \"permisoEnvio\": \"si\"\r\n" + 
//				"}";
//		agentService.setDatosAgente(new ObjectMapper().readValue(json, HashMap.class));
//		MultiValueMap params = new MultiValueMap();
//		params.put("category", "FUEGO");
//		params.put("windVelocity", "120");
//		
//		MockHttpServletRequestBuilder request = post("/incidencia/create").session(session)
//				.params((org.springframework.util.MultiValueMap<String, String>) params);
//		mockMvc.perform(request).andExpect(view().name("incidencia/create"));
//	}
}
