package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.http.ErrorResponse;
import edu.itu.cavabunga.core.http.ParameterResponse;
import edu.itu.cavabunga.core.http.PropertyResponse;
import edu.itu.cavabunga.core.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/property")
@Api(value = "Property controller", description = "Operations about properties")
public class PropertyController {

    private CalendarManagerService calendarManagerService;

    @Autowired
    public PropertyController(CalendarManagerService calendarManagerService) {
        this.calendarManagerService = calendarManagerService;
    }

    @ApiOperation(value = "Create new property")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created property", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Parent component with id = {parent_component_id} not found", response = ErrorResponse.class)
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createProperty(@RequestParam(value = "parent_component_id") Long parentComponentId,
                                   @RequestBody Property property) {
        calendarManagerService.addProperty(property, parentComponentId);
        return new Response(0,"created");
    }

    @ApiOperation(value = "Get property with id = {property_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrive property with id = {property_id}", response = PropertyResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Property with id = {property_id} not found", response = ErrorResponse.class)
    })
    @GetMapping(value = "/{property_id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse getProperty(@PathVariable(value = "property_id") Long propertyId) {
        return new PropertyResponse(0,null,calendarManagerService.getPropertyById(propertyId));
    }

    @ApiOperation(value = "Update property with id = {property_id}")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully update property with id = {property_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Property with id = {property_id} not found", response = ErrorResponse.class)
    })
    @PutMapping(value = "/{property_id}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response updateProperty(@PathVariable(value = "property_id") Long propertyId,
                                   @RequestBody Property property) {
        calendarManagerService.updateProperty(propertyId,property);
        return new Response(0,"updated");
    }

    @ApiOperation(value = "Delete property with id = {property_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully delete property with id = {property_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Property with id = {property_id} not found", response = ErrorResponse.class)
    })
    @DeleteMapping("/{property_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteProperty(@PathVariable(value = "property_id") Long propertyId) {
        calendarManagerService.deleteProperty(propertyId);
        return new Response(0,"deleted");
    }

    @ApiOperation(value = "Get parameters of property with id = {property_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieve parameters of property with id = {property_id}", response = ParameterResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Property with id = {property_id} not found", response = ErrorResponse.class)
    })
    @GetMapping("/{property_id}/parameter")
    @ResponseStatus(HttpStatus.OK)
    public ParameterResponse getPropertyParameters(@PathVariable(value = "property_id") Long propertyId){
        return new ParameterResponse(0,null, calendarManagerService.getParametersOfProperty(propertyId));
    }

}
