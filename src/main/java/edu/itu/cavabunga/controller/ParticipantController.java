package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.http.ErrorResponse;
import edu.itu.cavabunga.core.http.ParticipantResponse;
import edu.itu.cavabunga.core.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/participant")
@Api(value = "Participant controller", description = "Operations about participants")
public class ParticipantController {
   private CalendarManagerService calendarManagerService;

    @Autowired
    public ParticipantController(CalendarManagerService calendarManagerService) {
        this.calendarManagerService = calendarManagerService;
    }

    @ApiOperation(value ="View all participants available", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of participants",response = ParticipantResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No participants found", response = ErrorResponse.class),
    })
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantResponse getAllParticipants(){
        return new ParticipantResponse(0,null, calendarManagerService.getAllParticipants());
    }

    @ApiOperation(value = "Create new participant", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create a new participant", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorResponse.class)
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response newParticipant(@RequestBody Participant participant){
        calendarManagerService.addParticipant(participant);
        return new Response(0,"created");
    }

    @ApiOperation(value = "Get participant with username = {user_name}", response = Object.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrive a participant with username = {user_name}", response = ParticipantResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No participant found with username = {user_name}", response = ErrorResponse.class)
    })
    @GetMapping(value = "/{user_name}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantResponse getParticipant(@PathVariable(value = "user_name") String userName){
        return new ParticipantResponse(0,null,calendarManagerService.getParticipantByUserName(userName));
    }

    @ApiOperation(value = "Delete participant with id = {user_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully delete participant with id = {user_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No participant found with id = {user_id}", response = ErrorResponse.class)
    })
    @DeleteMapping(value = "/{user_id}", produces = "appliction/json")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteParticipant(@PathVariable(value = "user_id") Long id){
       calendarManagerService.deleteParticipantById(id);
       return new Response(0,"deleted");
    }

    @ApiOperation(value = "Update participant with id = {user_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully update participant with id = {user_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No participant found with id = {user_id}", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorResponse.class)
    })
    @PutMapping(value = "/{user_id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Response updateParticipant(@PathVariable(value = "user_id") Long id, @RequestBody Participant participant){
       calendarManagerService.updateParticipant(id, participant);
       return new Response(0,"updated");
    }
}
