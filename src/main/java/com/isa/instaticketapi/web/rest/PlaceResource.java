package com.isa.instaticketapi.web.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.Repertory;
import com.isa.instaticketapi.domain.Seat;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.service.PlaceService;
import com.isa.instaticketapi.service.dto.places.PlaceDTO;
import com.isa.instaticketapi.web.rest.vm.VoteForPlaceResponse;
import com.isa.instaticketapi.web.rest.vm.EventResponse.EventsResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.CinemaResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.PlaceResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.QuickSeatrsResponse;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.TheaterResponse;
import com.isa.instaticketapi.web.rest.vm.RepertoryResponse.RepertoryResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/place")
public class PlaceResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private PlaceService placeService;

	/**
	 * GET: /getCinemas: return list of all cinemas.
	 * 
	 * @return list of all cinemas
	 */
	@ApiOperation(value = "List of all cinemas", response = CinemaResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/cinemas")
	public ResponseEntity<CinemaResponse> getCinemas() {
		List<Place> cinemas = placeService.getCinemas();
		return new ResponseEntity<>(new CinemaResponse(cinemas), HttpStatus.OK);
	}

	/**
	 * GET: /getCinemas: return list of all theaters.
	 * 
	 * @return list of all theaters
	 */
	@ApiOperation(value = "List of all theaters.", response = TheaterResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/theaters")
	public ResponseEntity<TheaterResponse> getTheaters() {
		List<Place> theaters = placeService.getTheaters();
		return new ResponseEntity<>(new TheaterResponse(theaters), HttpStatus.OK);
	}

	/**
	 * GET: /getPlace: return all data for concrete place.
	 * 
	 * @param id
	 *            id of place about whom I am looking for information
	 * @return place(theater or cinema)
	 */
	@ApiOperation(value = "All data for place.", response = PlaceResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/place/{id}")
	public ResponseEntity<PlaceResponse> getPlace(@PathVariable("id") Long id) {
		Place place = placeService.getPlace(id);
		if (place == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		return new ResponseEntity<>(new PlaceResponse(place), HttpStatus.OK);
	}

	/**
	 * PUT: editPlace/{id} : edit data about concrete place
	 * 
	 * @param changePlaceDTO
	 *            data for editing
	 * @param id
	 *            id from place we want to change
	 * 
	 */
	@ApiOperation(value = "Edit place", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PutMapping("/place/{id}")
	public void editPlace(@RequestBody PlaceDTO placeDTO, @PathVariable("id") Long id) {
		if (placeService.changePlace(placeDTO, id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		placeService.changePlace(placeDTO, id);
	}

	/**
	 * POST: create-place : create new place by super-admin
	 * 
	 * @throws SQLException
	 * 
	 */
	@ApiOperation(value = "Creating new place", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created place"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/place")
	public void createPlace(@RequestBody PlaceDTO placeDTO) throws SQLException {

		log.debug("Rest request to create new Place : {}");

		placeService.createPlace(placeDTO);

	}

	/**
	 * POST: place/{id} : delete data about concrete place
	 * 
	 * @param id
	 *            id from place we want to delete
	 */
	@ApiOperation(value = "Delete Place", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/place/{id}")
	public void deleteEvent(@PathVariable("id") Long id) {
		if (placeService.deletePlace(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		placeService.deletePlace(id);
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list of event object in place
	 */
	@ApiOperation(value = "Get all Event in Place", response = EventsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("{id}/event-in-place")
	public ResponseEntity<EventsResponse> getEventsInPlace(@PathVariable("id") Long id) {
		ArrayList<Event> events = placeService.getEventsInPlace(id);
		return new ResponseEntity<>(new EventsResponse(events), HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list of repertories(objects) in place
	 * 
	 */

	@ApiOperation(value = "Get all Repertories in Place", response = RepertoryResponse.class)

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/{id}/repertories")
	public ResponseEntity<RepertoryResponse> getRepertoriesInPlace(@PathVariable("id") Long id) {
		if (placeRepository.findOneById(id) == null) {
			throw new IllegalArgumentException("Invalid id !");
		}
		ArrayList<Repertory> reprtories = placeService.getRepertoriesInPlace(id);
		return new ResponseEntity<>(new RepertoryResponse(reprtories), HttpStatus.OK);
	}

	/**
	 * 
	 * @param id id of place 
	 * @return place vote
	 */
	@ApiOperation(value = "Get all vote for Place", response = VoteForPlaceResponse.class)

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/{id}/vote")
	public ResponseEntity<VoteForPlaceResponse> getVoteForPlace(@PathVariable("id") Long id) {
		if (placeRepository.findOneById(id) == null) {
			throw new IllegalArgumentException("Invalid id !");
		}
		int vote = placeService.getVoteForPlace(id);
		return new ResponseEntity<>(new VoteForPlaceResponse(vote), HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 *            id of place
	 * @return list objects quick seats
	 */
	@ApiOperation(value = "Get all quick seats for Place", response = QuickSeatrsResponse.class)

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/{id}/quick-seats")
	public ResponseEntity<QuickSeatrsResponse> getQuickSeats(@PathVariable("id") Long id) {
		if (placeRepository.findOneById(id) == null) {
			throw new IllegalArgumentException("Invalid id !");
		}
		ArrayList<Seat> seats = placeService.getQuickSeats(id);
		return new ResponseEntity<>(new QuickSeatrsResponse(seats), HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 *            id of seat for quick reservation
	 */
	@ApiOperation(value = "Reservation for  quick seats for Place", response = HttpStatus.class)

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PutMapping("/quick-seats/{id}")
	public void reservation(@PathVariable("id") Long id) {
		placeService.reservation(id);

	}

}
