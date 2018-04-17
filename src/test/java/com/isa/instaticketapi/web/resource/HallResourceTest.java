package com.isa.instaticketapi.web.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.web.rest.vm.HallResponse.HallResponse;
import com.isa.instaticketapi.web.rest.vm.HallResponse.HallsResponse;
import com.isa.instaticketapi.web.rest.vm.Projection.ProjectionsResponse;

public class HallResourceTest extends AbstractResourceTest {
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	@Transactional
	public void testDeleteHallSuccesfull() throws Exception {
		String uri = "/api/hall/hall/8";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testDeleteHallUnsuccesfull() throws Exception {
		String uri = "/api/hall/hall/8888";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(400, status);
	}

	@Test
	@Transactional
	public void testGetHallsInPlaceSuccesfull() throws Exception {
		String uri = "/api/hall/3/halls-in-place";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		HallsResponse hallResponse = mapFromJson(result.getResponse().getContentAsString(), HallsResponse.class);
		Assert.assertEquals(3, hallResponse.getHalls().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetHallsInPlaceNullSuccesfull() throws Exception {
		String uri = "/api/hall/1/halls-in-place";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		HallsResponse hallResponse = mapFromJson(result.getResponse().getContentAsString(), HallsResponse.class);
		Assert.assertEquals(0, hallResponse.getHalls().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetHallDataSuccesfull() throws Exception {
		String uri = "/api/hall/hall/1";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		HallResponse hallResponse = mapFromJson(result.getResponse().getContentAsString(), HallResponse.class);
		Assert.assertEquals("Sala 1.", hallResponse.getHall().getName());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetProjectionsInHallSuccesfull() throws Exception {
		String uri = "/api/hall/8/projections-in-hall";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ProjectionsResponse projectionsResponse = mapFromJson(result.getResponse().getContentAsString(), ProjectionsResponse.class);
		Assert.assertEquals(3, projectionsResponse.getProjections().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

}
