package com.isa.instaticketapi.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.service.VoteForPlaceService;
import com.isa.instaticketapi.service.VoteForEventService;
import com.isa.instaticketapi.service.dto.places.VoteForPlaceDTO;
import com.isa.instaticketapi.service.dto.projection.VoteForEventDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/vote-for-event")
public class VoteForEventResource {
	@Autowired
	private VoteForEventService voteForEventService;

	@ApiOperation(value = "Creating new vote for event", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created vote for projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/vote")
	public void createVote(@RequestBody VoteForEventDTO voteForEventDTO) {

		voteForEventService.createVote(voteForEventDTO);

	}

	@ApiOperation(value = "Check if he voted for event", response = HttpStatus.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@GetMapping("/check-vote/{id}")
	public boolean checkIfHeVoted(@PathVariable("id") Long id) {
		return voteForEventService.checkIfHeVoted(id);
	}

}
