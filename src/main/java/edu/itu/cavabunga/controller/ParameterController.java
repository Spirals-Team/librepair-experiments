package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.http.ErrorResponse;
import edu.itu.cavabunga.core.http.ParameterResponse;
import edu.itu.cavabunga.core.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/parameter")
@Api(value = "Participant controller", description = "Operations about parameters")
public class ParameterController {

    private CalendarManagerService calendarManagerService;

    @Autowired
    public ParameterController(CalendarManagerService calendarManagerService) {
        this.calendarManagerService = calendarManagerService;
    }

    @ApiOperation(value = "Create new parameter with parent_id = parent_property_id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully new parameter created with parent_id = {parent_property_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No property found with id = {parent_property_id}", response = ErrorResponse.class),
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createParameter(@RequestParam(value = "parent_property_id") Long parentPropertyId,
                                    @RequestBody Parameter parameter) {
        calendarManagerService.addParameter(parameter,parentPropertyId);
        return new Response(0,"created");
    }

    @ApiOperation(value = "Get parameter with id = {paremeter_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrive paraemeter with id = {parameter_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No parameter found with id = {parameter_id}", response = ErrorResponse.class),
    })
    @GetMapping(value = "/{parameter_id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ParameterResponse getParameter(@PathVariable(value = "parameter_id") Long parameterId) {
        return new ParameterResponse(0,null, calendarManagerService.getParameterById(parameterId));
    }


    @ApiOperation(value = "Update parameter with id = {paremeter_id}")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully update parameter with id = {parameter_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No parameter found with id = {parameter_id}", response = ErrorResponse.class),
    })
    @PutMapping(value = "/{parameter_id}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response updateParameter(@PathVariable(value = "parameter_id") Long parameterId,
                                    @RequestBody Parameter parameter) {
        calendarManagerService.updateParameter(parameter, parameterId);
        return new Response(0,"updated");
    }

    @ApiOperation(value = "Delete parameter with id = {parameter_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully delete parameter with id = {parameter_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No parameter found with id = {parameter_id}", response = ErrorResponse.class),
    })
    @DeleteMapping(value = "/{parameter_id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteParameter(@PathVariable(value = "parameter_id") Long parameterId) {
        calendarManagerService.deleteParameter(parameterId);
        return new Response(0,"deleted");
    }
}
