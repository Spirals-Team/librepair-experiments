package com.isa.instaticketapi.web.rest;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * 
 * @author milica
 *
 */

import com.isa.instaticketapi.domain.Hall;
import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.service.HallService;
import com.isa.instaticketapi.service.dto.places.HallDTO;
import com.isa.instaticketapi.web.rest.vm.HallResponse.HallResponse;
import com.isa.instaticketapi.web.rest.vm.HallResponse.HallsResponse;
import com.isa.instaticketapi.web.rest.vm.Projection.ProjectionsResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/hall")
public class HallResource {
	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Autowired
	private HallService hallService;

	/**
	 * 
	 * @param hallDTO
	 *            object providing information about new hall
	 * @param id
	 *            of place
	 */

	@ApiOperation(value = "Creating new hall", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created hall"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/hall/{id}")
	public void createHall(@RequestBody HallDTO hallDTO, @PathVariable("id") Long id) {
		log.debug("REST request to create Hall : {}", hallDTO);
		hallService.createHall(hallDTO, id);
	}

	/**
	 * 
	 * @param id
	 *            id of hall for delete
	 */
	@ApiOperation(value = "Delete hall", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/hall/{id}")
	public void deleteHall(@PathVariable("id") Long id) {
		if (hallService.deleteHall(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		hallService.deleteHall(id);
	}

	/**
	 * 
	 * @param id
	 *            of place
	 * @return list of halls in place
	 */
	@ApiOperation(value = "Halls in place.", response = HallsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/{id}/halls-in-place")
	public ResponseEntity<HallsResponse> getHallsInPlace(@PathVariable("id") Long id) {

		if (hallService.getHalls(id) == null) {
			throw new IllegalArgumentException("Invalid id or no halls in place!");
		}
		ArrayList<Hall> halls = hallService.getHalls(id);
		return new ResponseEntity<>(new HallsResponse(halls), HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 *            id of hall
	 * @return object hall
	 */

	@ApiOperation(value = "All data for hall.", response = HallsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/hall/{id}")
	public ResponseEntity<HallResponse> getCinema(@PathVariable("id") Long id) {
		Hall hall = hallService.getHall(id);
		if (hall == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		return new ResponseEntity<>(new HallResponse(hall), HttpStatus.OK);
	}

	/**
	 * 
	 * @param hallDTO
	 *            data for editing
	 * @param id
	 *            id of hall for change
	 */
	@ApiOperation(value = "Edit hall", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@PutMapping("/hall/{id}")
	public void editHall(@RequestBody HallDTO hallDTO, @PathVariable("id") Long id) {

		if (hallService.editHall(hallDTO, id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		hallService.editHall(hallDTO, id);
	}

	/**
	 * 
	 * @param id
	 *            id of hall
	 * @return list of objects (Projection) in hall
	 */

	@ApiOperation(value = "Get all projections in hall.", response = ProjectionsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/{id}/projections-in-hall")
	public ResponseEntity<ProjectionsResponse> getAllProjectionInHall(@PathVariable("id") Long id) {
		ArrayList<Projection> projections = hallService.getAllProjectionInHall(id);
		return new ResponseEntity<>(new ProjectionsResponse(projections), HttpStatus.OK);
	}
}
