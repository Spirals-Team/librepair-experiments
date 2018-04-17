package com.isa.instaticketapi.web.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.web.rest.vm.Projection.ProjectionResponse;

public class ProjectionResourceTest extends AbstractResourceTest {
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	@Transactional
	public void testGetProjectionDataSuccesfull() throws Exception {
		String uri = "/api/projection/projection/1";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ProjectionResponse projectionResponse = mapFromJson(result.getResponse().getContentAsString(),
				ProjectionResponse.class);
		Assert.assertEquals("23-4-2018", projectionResponse.getProjection().getDate());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}
/*
	@Test
	@Transactional
	public void testGetProjectionDataUnsuccesfull() throws Exception {
		String uri = "/api/projection/projection/8888";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(400, status);
	}

	@Test
	@Transactional
	public void testDeleteProjectionUnsuccesfull() throws Exception {
		String uri = "/api/projection/projection/8888";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(400, status);
	}
*/
}
