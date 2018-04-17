package com.isa.instaticketapi.web.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.web.rest.vm.EventResponse.EventResponse;

public class EventResourceTest extends AbstractResourceTest {
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	@Transactional
	public void testGetEventDataSuccesfull() throws Exception {
		String uri = "/api/event/event/1";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		EventResponse eventResponse = mapFromJson(result.getResponse().getContentAsString(), EventResponse.class);
		Assert.assertEquals("Tomb Raider", eventResponse.getEvent().getName());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetEventDataUnsuccesfull() throws Exception {
		String uri = "/api/event/event/8888";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(400, status);
	}
	
	@Test
	@Transactional
	public void testDeleteEventUnsuccesfull() throws Exception {
		String uri = "/api/event/event/8888";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(400, status);
	}

}
