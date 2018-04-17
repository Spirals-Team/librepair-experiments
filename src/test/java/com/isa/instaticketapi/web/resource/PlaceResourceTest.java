package com.isa.instaticketapi.web.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.web.rest.vm.VoteForPlaceResponse;
import com.isa.instaticketapi.web.rest.vm.EventResponse.EventsResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.CinemaResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.PlaceResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.QuickSeatrsResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.TheaterResponse;

public class PlaceResourceTest extends AbstractResourceTest {

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	@Transactional
	public void testGetCinemasSuccesfull() throws Exception {
		String uri = "/api/place/cinemas";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(mapFromJson(result.getResponse().getContentAsString(), CinemaResponse.class));
		CinemaResponse cinemaResponse = mapFromJson(result.getResponse().getContentAsString(), CinemaResponse.class);
		Assert.assertNotNull(cinemaResponse.getCinemas());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetTheathersSuccesfull() throws Exception {
		String uri = "/api/place/theaters";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		System.out.println(mapFromJson(result.getResponse().getContentAsString(), TheaterResponse.class));
		TheaterResponse cinemaResponse = mapFromJson(result.getResponse().getContentAsString(), TheaterResponse.class);
		Assert.assertNotNull(cinemaResponse.getTheaters());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetPlaceSuccesfull() throws Exception {
		String uri = "/api/place/place/1";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		PlaceResponse placeResponse = mapFromJson(result.getResponse().getContentAsString(), PlaceResponse.class);
		Assert.assertEquals("Srpsko narodno pozoriste", placeResponse.getPlace().getName());
		Assert.assertNotNull(placeResponse.getPlace());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetPlaceUnsuccesfull() throws Exception {
		String uri = "/api/place/place/11aa";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		Assert.assertEquals(400, result.getResponse().getStatus());
	}

	@Test
	@Transactional
	public void testDeletePlaceUnsuccesfull() throws Exception {
		String uri = "/api/place/place/1111111";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		Assert.assertEquals(405, result.getResponse().getStatus());
	}

	@Test
	@Transactional
	public void testGetAllEventInPlaceSuccesfull() throws Exception {
		String uri = "/api/place/3/event-in-place";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		EventsResponse eventsResponse = mapFromJson(result.getResponse().getContentAsString(), EventsResponse.class);
		Assert.assertNotNull(eventsResponse.getEvents());
		Assert.assertEquals(4, eventsResponse.getEvents().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	///
	@Test
	@Transactional
	public void testGetAllEventInPlaceUnsuccesfull() throws Exception {
		String uri = "/api/place/33333/event-in-place";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		Assert.assertEquals(405, result.getResponse().getStatus());
	}

	@Test
	@Transactional
	public void testGetVoteForPlaceSuccesfull() throws Exception {
		String uri = "/api/place/3/vote";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		VoteForPlaceResponse voteForPlace = mapFromJson(result.getResponse().getContentAsString(),
				VoteForPlaceResponse.class);
		Assert.assertEquals(5, voteForPlace.getVote());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetAllQuickSeatsForPlaceSuccesfull() throws Exception {
		String uri = "/api/place/3/quick-seats";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		QuickSeatrsResponse quickSeats = mapFromJson(result.getResponse().getContentAsString(),
				QuickSeatrsResponse.class);
		Assert.assertNotNull(quickSeats.getSeats());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	@Test
	@Transactional
	public void testGetAllQuickSeatsForPlaceNullSuccesfull() throws Exception {
		String uri = "/api/place/2/quick-seats";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		QuickSeatrsResponse quickSeats = mapFromJson(result.getResponse().getContentAsString(),
				QuickSeatrsResponse.class);
		Assert.assertEquals(true, quickSeats.getSeats().isEmpty());
		Assert.assertEquals(0, quickSeats.getSeats().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

	/*
	 * @Test
	 * 
	 * @Transactional public void testQuickSeatReservationSuccesfull() throws
	 * Exception { String uri = "/api/place/quick-seats/3"; MvcResult result =
	 * mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.
	 * APPLICATION_JSON) .accept(MediaType.APPLICATION_JSON)).andReturn(); int
	 * status = result.getResponse().getStatus(); Assert.assertEquals(200,
	 * status); }
	 */

}
