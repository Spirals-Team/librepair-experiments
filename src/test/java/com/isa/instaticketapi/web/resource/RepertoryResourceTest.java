package com.isa.instaticketapi.web.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.web.rest.vm.Projection.ProjectionsResponse;

public class RepertoryResourceTest extends AbstractResourceTest {

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	@Transactional
	public void testGetAllProjectionsInRepertorySuccesfull() throws Exception {
		String uri = "/api/repertory/all-projections/1";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ProjectionsResponse projecitonResponse = mapFromJson(result.getResponse().getContentAsString(),
				ProjectionsResponse.class);
		Assert.assertEquals(2, projecitonResponse.getProjections().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetAllProjectionsInRepertoryNullSuccesfull() throws Exception {
		String uri = "/api/repertory/all-projections/5";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ProjectionsResponse projecitonResponse = mapFromJson(result.getResponse().getContentAsString(),
				ProjectionsResponse.class);
		Assert.assertEquals(0, projecitonResponse.getProjections().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

}
