package com.isa.instaticketapi.web.rest;

import java.sql.SQLException;

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
/**
 * REST controller for managing the current projection.
 * @author Milica Kovacevic
 *
 */

import com.isa.instaticketapi.domain.Event;
import com.isa.instaticketapi.service.EventService;
import com.isa.instaticketapi.service.dto.places.ChangeEventDTO;
import com.isa.instaticketapi.service.dto.places.EventDTO;
import com.isa.instaticketapi.web.rest.vm.EventResponse.EventResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/event")
public class EventResource {
	private final Logger log = LoggerFactory.getLogger(EventResource.class);

	@Autowired
	private EventService eventService;

	/**
	 * 
	 * @param EventDTO
	 *            object providing information about new event
	 */

	@ApiOperation(value = "Creating new event", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created event"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/event/{id}")
	public void createEvent(@RequestBody EventDTO eventDTO, @PathVariable("id") Long id) throws SQLException {
		log.debug("REST request to create Event : {}", eventDTO);
		eventService.createEvent(eventDTO, id);
	}

	/**
	 * GET: /event: return all data for concrete event.
	 * 
	 * @param id
	 *            id of event about whom I am looking for information
	 * @return object event
	 */
	@ApiOperation(value = "All data for event.", response = EventResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("event/{id}")
	public ResponseEntity<EventResponse> Event(@PathVariable("id") Long id) {
		if (eventService.getEvent(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		Event event = eventService.getEvent(id);
		return new ResponseEntity<>(new EventResponse(event), HttpStatus.OK);
	}

	/**
	 * POST: event/{id} : edit data about concrete event
	 * 
	 * @param changeEventDTO
	 *            data for editing
	 * @param id
	 *            id from event we want to change
	 */
	@ApiOperation(value = "Edit event", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PutMapping("/event/{id}")
	public void editEvent(@RequestBody ChangeEventDTO changeEventDTO, @PathVariable("id") Long id) {
		if (eventService.changeEvent(changeEventDTO, id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		eventService.changeEvent(changeEventDTO, id);
	}

	/**
	 * POST: event/{id} : delete data about concrete event
	 * 
	 * @param id
	 *            id from event we want to delete
	 */
	@ApiOperation(value = "Delete Event", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/event/{id}")
	public void deleteEvent(@PathVariable("id") Long id) {
		if (eventService.deleteEvent(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		eventService.deleteEvent(id);
	}

}
