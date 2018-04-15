package com.isa.instaticketapi.web.rest;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.Projection;
import com.isa.instaticketapi.repository.RepertotyRepository;
import com.isa.instaticketapi.service.RepertoryService;
import com.isa.instaticketapi.service.dto.Repertory.RepertoryDTO;
import com.isa.instaticketapi.web.rest.vm.Projection.ProjectionsResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/repertory")
public class RepertoryResource {

	@Autowired
	private RepertoryService repertoryService;

	@Autowired
	private RepertotyRepository repertoryRepository;

	/**
	 * 
	 * @param repertoryDTO
	 *            object for creating new repertory
	 * @param id
	 *            id of place for repertory
	 */
	@ApiOperation(value = "Creating new repertory", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created repertory"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{id}/repertory")
	public void createRepertory(@RequestBody RepertoryDTO repertoryDTO, @PathVariable("id") Long id) {

		repertoryService.createRepertory(repertoryDTO, id);

	}

	/**
	 * 
	 * @param id
	 *            id of repertory
	 */
	@ApiOperation(value = "Deleting repertory.", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully delete repertory"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("repertory/{id}")
	public void deleteRepertory(@PathVariable("id") Long id) {
		if (repertoryService.deleteRepertory(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		repertoryService.deleteRepertory(id);
	}

	/**
	 * 
	 * @param id
	 *            id of reprtory
	 * @return list of objects(Projections) in reprtory
	 */
	@ApiOperation(value = "Get all projections in repertory.", response = ProjectionsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully "),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })

	@GetMapping("/all-projections/{id}")
	public ResponseEntity<ProjectionsResponse> getAllProjectionsInRepertory(@PathVariable("id") Long id) {
		if (repertoryRepository.findOneById(id) == null) {
			throw new IllegalArgumentException("Invalid id!");
		}
		ArrayList<Projection> projections = repertoryService.getAllProjectionsInRepertory(id);
		return new ResponseEntity<>(new ProjectionsResponse(projections), HttpStatus.OK);

	}

}
