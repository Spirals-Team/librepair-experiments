package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.controller.wrapper.ComponentResponse;
import edu.itu.cavabunga.controller.wrapper.ParameterResponse;
import edu.itu.cavabunga.controller.wrapper.PropertyResponse;
import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/participant")
public class CalendarController {
    @Autowired
    private CalendarManagerService calendarManagerService;

    @Autowired
    private ComponentResponse componentResponse;

    @Autowired
    private PropertyResponse propertyResponse;

    @Autowired
    private ParameterResponse parameterResponse;

    @GetMapping("/{user_key}/calendar")
    @ResponseStatus(HttpStatus.OK)
    public ComponentResponse getParticipantCalendars(@PathVariable(value = "user_key") String userName){
        return componentResponse.createComponentResponseForList(0,null,calendarManagerService.getComponentsByParticipantAndType(userName, ComponentType.Calendar));
    }

    @PostMapping("/{user_key}/calendar")
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentResponse newCalendarForParticipant(@PathVariable(value = "user_key") String userName, @RequestBody Component component){
        calendarManagerService.addComponent(component,userName,null);
        return componentResponse.createComponentResponseForSingle(0,"created",null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/")
    @ResponseStatus(HttpStatus.OK)
    public ComponentResponse getParticipantCalendar(@PathVariable(value = "user_key") String userName , @PathVariable(value = "calendar_id") Long id){
        return componentResponse.createComponentResponseForSingle(0,null,calendarManagerService.getComponentById(id));
    }

    @PutMapping("/{user_key}/calendar/{calendar_id}/")
    @ResponseStatus(HttpStatus.OK)
    public ComponentResponse updateParticipantCalendar(@PathVariable(value = "user_key") String userName, @PathVariable(value = "calendar_id") Long id, @RequestBody Component component){
        calendarManagerService.updateComponent(id,component);
        return componentResponse.createComponentResponseForSingle(0,"update", null);
    }

    @DeleteMapping("/{user_key}/calendar/{calendar_id}/")
    @ResponseStatus(HttpStatus.OK)
    public ComponentResponse deleteParticipantCalendar(@PathVariable(value = "user_key") String userName, @PathVariable(value = "calendar_id") Long id){
        calendarManagerService.deleteComponentById(id);
        return componentResponse.createComponentResponseForSingle(0,"delete", null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/property")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse getParticipantCalendarProperties(@PathVariable(value = "user_key") String userName, @PathVariable(value = "calendar_id")Long id){
        return propertyResponse.createPropertyResponseForList(0,null,calendarManagerService.getPropertyForComponent(id));
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/property/{property_id}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse getParticipantCalendarProperty(@PathVariable(value = "user_key") String userName,
                                                             @PathVariable(value = "calendar_id")Long id,
                                                             @PathVariable(value = "property_id") Long propertyId){
        return propertyResponse.createPropertyResponseForSingle(0,null,calendarManagerService.getPropertyById(propertyId));
    }

    @PostMapping("/{user_key}/calendar/{calendar_id}/property")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResponse newParticipantCalendarProperty(@PathVariable(value = "user_key") String userName, @PathVariable(value = "calendar_id") Long id, @RequestBody Property property){
        calendarManagerService.addProperty(property, userName, id);
        return propertyResponse.createPropertyResponseForSingle(0,"create", null);
    }

    @PutMapping("/{user_key}/calendar/{calendar_id}/property/{property_id}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse updateParticipantCalendarProperty(@PathVariable(value = "user_key") String userName,
                                                              @PathVariable(value = "calendar_id") Long id,
                                                              @PathVariable(value = "property_id") Long propertyId,
                                                              @RequestBody Property property){
        calendarManagerService.updateProperty(propertyId,property);
        return propertyResponse.createPropertyResponseForSingle(0,"update", null);
    }

    @DeleteMapping("/{user_key}/calendar/{calendar_id}/property/{property_id}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse deleteParticipantCalendarProperty(@PathVariable(value = "user_key") String userName,
                                                              @PathVariable(value = "calendar_id") Long id,
                                                              @PathVariable(value = "property_id") Long propertyId){
        calendarManagerService.deleteProperty(propertyId);
        return propertyResponse.createPropertyResponseForSingle(0,"delete", null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/{component_type}")
    @ResponseStatus(HttpStatus.OK)
    public ComponentResponse getParticipantCalendarComponents(@PathVariable(value = "user_key") String userName,
                                                             @PathVariable(value = "calendar_id") Long id,
                                                             @PathVariable(value = "component_type") String componentType){
        return componentResponse.createComponentResponseForList(0,null, calendarManagerService.getComponentsByParticipantAndType(userName,ComponentType.valueOf(Character.toUpperCase(componentType.charAt(0)) + componentType.substring(1))));
    }

    @PostMapping("/{user_key}/calendar/{calendar_id}/{component_type}")
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentResponse newParticipantCalendarComponent(@PathVariable(value = "user_key") String userName,
                                                             @PathVariable(value = "calendar_id") Long id,
                                                             @PathVariable(value = "component_type") String componentType, Component component){
        calendarManagerService.addComponent(component,userName,id);
        return componentResponse.createComponentResponseForSingle(0,"update",null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}")
    @ResponseStatus(HttpStatus.OK)
    public ComponentResponse getParticipantCalendarComponent(@PathVariable(value = "user_key") String userName,
                                                             @PathVariable(value = "calendar_id") Long calendarId,
                                                             @PathVariable(value = "component_type") String componentType,
                                                             @PathVariable(value = "component_id") Long componentId){
        return componentResponse.createComponentResponseForSingle(0,null,calendarManagerService.getComponentById(componentId));
    }

    @PostMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentResponse newParticipantCalendarSubComponent(@PathVariable(value = "user_key") String userName,
                                                                @PathVariable(value = "calendar_id") Long calendarId,
                                                                @PathVariable(value = "component_type") String componentType,
                                                                @PathVariable(value = "component_id") Long componentId,
                                                                @RequestBody Component component){
        calendarManagerService.addComponent(component,userName,componentId);
        return componentResponse.createComponentResponseForSingle(0,"sub component added",null);
    }

    @PutMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentResponse updateParticipantCalendarComponent(@PathVariable(value = "user_key") String userName,
                                                             @PathVariable(value = "calendar_id") Long calendarId,
                                                             @PathVariable(value = "component_type") String componentType,
                                                             @PathVariable(value = "component_id") Long componentId,
                                                             @RequestBody Component component){
        calendarManagerService.updateComponent(componentId, component);
        return componentResponse.createComponentResponseForSingle(0,"update", null);
    }

    @DeleteMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ComponentResponse deleteParticipantCalendarComponent(@PathVariable(value = "user_key") String userName,
                                                             @PathVariable(value = "calendar_id") Long calendarId,
                                                             @PathVariable(value = "component_type") String componentType,
                                                             @PathVariable(value = "component_id") Long componentId){
        calendarManagerService.deleteComponentById(componentId);
        return componentResponse.createComponentResponseForSingle(0,"delete", null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse getParticipantCalendarComponentProperties(@PathVariable(value = "user_key") String userName,
                                                                    @PathVariable(value = "calendar_id") Long calendarId,
                                                                    @PathVariable(value = "component_type") String componentType,
                                                                    @PathVariable(value = "component_id") Long componentId){
        return propertyResponse.createPropertyResponseForList(0,null, calendarManagerService.getPropertyForComponent(componentId));
    }

    @PostMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResponse newParticipantCalendarComponentProperty(@PathVariable(value = "user_key") String userName,
                                                                    @PathVariable(value = "calendar_id") Long calendarId,
                                                                    @PathVariable(value = "component_type") String componentType,
                                                                    @PathVariable(value = "component_id") Long componentId,
                                                                    @RequestBody Property property){
        calendarManagerService.addProperty(property,userName,componentId);
        return propertyResponse.createPropertyResponseForSingle(0,"created",null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse getParticipantCalendarComponentProperty(@PathVariable(value = "user_key") String userName,
                                                                    @PathVariable(value = "calendar_id") Long calendarId,
                                                                    @PathVariable(value = "component_type") String componentType,
                                                                    @PathVariable(value = "component_id") Long componentId,
                                                                    @PathVariable(value = "property_key") Long propertyId){
        return propertyResponse.createPropertyResponseForSingle(0,null,calendarManagerService.getPropertyById(propertyId));
    }

    @PutMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResponse updateParticipantCalendarComponentProperty(@PathVariable(value = "user_key") String userName,
                                                                    @PathVariable(value = "calendar_id") Long calendarId,
                                                                    @PathVariable(value = "component_type") String componentType,
                                                                    @PathVariable(value = "component_id") Long componentId,
                                                                    @PathVariable(value = "property_key") Long propertyId,
                                                                       @RequestBody Property property){
        calendarManagerService.updateProperty(propertyId,property);
        return propertyResponse.createPropertyResponseForSingle(0,"update",null);
    }

    @DeleteMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyResponse deleteParticipantCalendarComponentProperty(@PathVariable(value = "user_key") String userName,
                                                                    @PathVariable(value = "calendar_id") Long calendarId,
                                                                    @PathVariable(value = "component_type") String componentType,
                                                                    @PathVariable(value = "component_id") Long componentId,
                                                                    @PathVariable(value = "property_key") Long propertyId){
        calendarManagerService.deleteComponentById(propertyId);
        return propertyResponse.createPropertyResponseForSingle(0,"delete",null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}/parameter")
    @ResponseStatus(HttpStatus.OK)
    public ParameterResponse getParticipantCalendarComponentPropertyParameters(@PathVariable(value = "user_key") String userName,
                                                                              @PathVariable(value = "calendar_id") Long calendarId,
                                                                              @PathVariable(value = "component_type") String componentType,
                                                                              @PathVariable(value = "component_id") Long componentId,
                                                                              @PathVariable(value = "property_key") Long propertyId){
        return parameterResponse.createParameterResponseForList(0,null, calendarManagerService.getParametersByProperty(propertyId));
    }

    @PostMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}/parameter")
    @ResponseStatus(HttpStatus.CREATED)
    public ParameterResponse newParticipantCalendarComponentPropertyParameter(@PathVariable(value = "user_key") String userName,
                                                                              @PathVariable(value = "calendar_id") Long calendarId,
                                                                              @PathVariable(value = "component_type") String componentType,
                                                                              @PathVariable(value = "component_id") Long componentId,
                                                                              @PathVariable(value = "property_key") Long propertyId,
                                                                              @RequestBody Parameter parameter){
        calendarManagerService.addParameter(parameter,userName,componentId,propertyId);
        return parameterResponse.createParameterReponseForSingle(0,"create", null);
    }

    @GetMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}/parameter/{parameter_key}")
    @ResponseStatus(HttpStatus.OK)
    public ParameterResponse getParticipantCalendarComponentPropertyParameter(@PathVariable(value = "user_key") String userName,
                                                                               @PathVariable(value = "calendar_id") Long calendarId,
                                                                               @PathVariable(value = "component_type") String componentType,
                                                                               @PathVariable(value = "component_id") Long componentId,
                                                                               @PathVariable(value = "property_key") Long propertyId,
                                                                               @PathVariable(value = "parameter_key") Long parameterId){
        return parameterResponse.createParameterReponseForSingle(0,null, calendarManagerService.getParameterById(parameterId));
    }

    @PutMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}/parameter/{parameter_key}")
    @ResponseStatus(HttpStatus.OK)
    public ParameterResponse updateParticipantCalendarComponentPropertyParameter(@PathVariable(value = "user_key") String userName,
                                                                              @PathVariable(value = "calendar_id") Long calendarId,
                                                                              @PathVariable(value = "component_type") String componentType,
                                                                              @PathVariable(value = "component_id") Long componentId,
                                                                              @PathVariable(value = "property_key") Long propertyId,
                                                                              @PathVariable(value = "parameter_key") Long parameterId,
                                                                                 @RequestBody Parameter parameter){
        calendarManagerService.updateParameter(parameter, parameterId);
        return parameterResponse.createParameterReponseForSingle(0, "update", null);
    }

    @DeleteMapping("/{user_key}/calendar/{calendar_id}/{component_type}/{component_id}/property/{property_key}/parameter/{parameter_key}")
    @ResponseStatus(HttpStatus.OK)
    public ParameterResponse deleteParticipantCalendarComponentPropertyParameter(@PathVariable(value = "user_key") String userName,
                                                                              @PathVariable(value = "calendar_id") Long calendarId,
                                                                              @PathVariable(value = "component_type") String componentType,
                                                                              @PathVariable(value = "component_id") Long componentId,
                                                                              @PathVariable(value = "property_key") Long propertyId,
                                                                              @PathVariable(value = "parameter_key") Long parameterId){
        calendarManagerService.deleteParameter(parameterId);
        return parameterResponse.createParameterReponseForSingle(0,"delete", null);
    }
}