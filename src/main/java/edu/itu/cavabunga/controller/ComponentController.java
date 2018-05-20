package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.http.ComponentResponse;
import edu.itu.cavabunga.core.http.ErrorResponse;
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
@RequestMapping(path="/component")
@Api(value = "Component controller", description = "Operations about components")
public class ComponentController {

    private CalendarManagerService calendarManagerService;

    @Autowired
    public ComponentController(CalendarManagerService calendarManagerService) {
        this.calendarManagerService = calendarManagerService;
    }

    @ApiOperation(value = "Create new component with parent_id = {parent_component_id}")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully new component created with parent_id = {parent_component_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No component found with parent_id = {parent_component_id}", response = ErrorResponse.class),
    })
    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createComponent(@RequestParam(value = "user_name") String userName,
                                    @RequestParam(value = "parent_component_id") Long ParentComponentId,
                                    @RequestBody Component component) {
        calendarManagerService.addComponent(component, userName, ParentComponentId);
        return new Response(0,"created");
    }

    @ApiOperation(value = "Get component with id = {component_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully get component with id = {component_id}", response = ComponentResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No component found with id = {component_id}", response = ErrorResponse.class),
    })
    @GetMapping(value = "/{component_id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ComponentResponse getComponent(@PathVariable(value = "component_id")Long componentId){
        return new ComponentResponse(0,null,calendarManagerService.getComponentById(componentId));
    }

    @ApiOperation(value = "Update component with id = {component_id}")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully update component with id = {component_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No component found with id = {component_id}", response = ErrorResponse.class),
    })
    @PutMapping(value = "/{component_id}/", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Response updateComponent(@PathVariable(value = "component_id")Long componentId,
                                    @RequestBody Component component) {
        calendarManagerService.updateComponent(componentId, component);
        return new Response(0,"updated");
    }

    @ApiOperation(value = "Delete component with id = {component_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully delete component with id = {component_id}", response = Response.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No component found with id = {component_id}", response = ErrorResponse.class),
    })
    @DeleteMapping(value = "/{component_id}/", produces = "applicatioin/json")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteComponent(@PathVariable(value = "component_id")Long componentId) {
        calendarManagerService.deleteComponentById(componentId);
        return new Response(0,"deleted");
    }


    @ApiOperation(value = "Get component's property by component with id = {component_id}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully update component with id = {component_id}", response = PropertyResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized request", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "No parameter found for component with id = {component_id}", response = ErrorResponse.class),
    })
    @GetMapping(value = "/{component_id}/property", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse getComponentProperties(@PathVariable(value = "component_id")Long componentId) {
        return new PropertyResponse(0,null,calendarManagerService.getPropertiesOfComponent(componentId));
    }
}
