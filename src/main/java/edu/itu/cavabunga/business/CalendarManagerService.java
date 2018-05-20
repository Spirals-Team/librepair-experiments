package edu.itu.cavabunga.business;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.exception.*;

import java.util.List;

/**
 * CRUD service for all Calendar elements
 */
public interface CalendarManagerService {
    /**
     * add new participant
     *
     * @param participant participant to be added
     * @throws IllegalArgumentException when participant is null
     * @throws IllegalStateException when participant has id field
     * @throws Conflict Participant username should not conflict with any existing participant
     */
    void addParticipant(Participant participant);

    /**
     * get participant by username
     *
     * @param userName requested participant username
     * @return requested participant
     * @throws IllegalArgumentException when username is null
     * @throws NotFound when participant with given username not found
     */
    Participant getParticipantByUserName(String userName);

    /**
     * get list of the all participants
     *
     * @return list of the all participants
     */
    List<Participant> getAllParticipants();

    /**
     * delete a participant
     *
     * @param id participant id to be deleted
     * @throws IllegalArgumentException when participant id is null
     * @throws NotFound when participant with given username not found
     */
    void deleteParticipantById(Long id);

    /**
     * update a participant
     *
     * @param id participant id to be updated
     * @param participant participant to be updated
     * @throws IllegalArgumentException when id or participant input null | id and participant.id does not match
     * @throws NotFound when participant with given id not found
     */
    void updateParticipant(Long id, Participant participant);

    /**
     * add new component
     *
     * @param component component to be added
     * @throws IllegalArgumentException when any input is null
     * @throws IllegalStateException when the component has id field
     * @throws NotFound when owner or parent component not found
     */
    void addComponent(Component component, String owner, Long parentComponentId);

    /**
     * get component by id
     *
     * @param id requested component id
     * @return requested component
     * @throws IllegalArgumentException when id is null
     * @throws NotFound when component with given id not found
     */
    Component getComponentById(Long id);

    /**
     * delete a component
     *
     * @param id component id to be deleted
     * @throws IllegalArgumentException when component id is null
     * @throws NotFound when component with given id not found
     */
    void deleteComponentById(Long id);

    /**
     * update a component
     *
     * @param id component id to be updated
     * @param component component to be updated
     * @throws IllegalArgumentException when id or component input null | id and component.id does not match
     * @throws NotFound when component with given id not found
     */
    void updateComponent(Long id, Component component);

    /**
     * add new property
     *
     * @param property property to be added
     * @param parentComponentId parent component of new property
     * @throws IllegalArgumentException when any input is null
     * @throws IllegalStateException when the property has id field
     * @throws NotFound when parent component not found
     */
    void addProperty(Property property, Long parentComponentId);

    /**
     * get property by id
     *
     * @param propertyId requested property id
     * @return requested property
     * @throws IllegalArgumentException when id is null
     * @throws NotFound when property with given id not found
     */
    Property getPropertyById(Long propertyId);

    /**
     * returns all properties of the given component
     *
     * @param componentId component that properties are bound to
     * @return list of the bounded properties
     * @throws IllegalArgumentException when componentId is null
     * @throws NotFound when component with given Id not found
     */
    List<Property> getPropertiesOfComponent(Long componentId);

    /**
     * delete a property
     *
     * @param propertyId property id to be deleted
     * @throws IllegalArgumentException when property id is null
     * @throws NotFound when property with given id not found
     */
    void deleteProperty(Long propertyId);

    /**
     * update a property
     *
     * @param propertyId property id to be updated
     * @param property property to be updated
     * @throws IllegalArgumentException when id or property input null | id and property.id does not match
     * @throws NotFound when property with given id not found
     */
    void updateProperty(Long propertyId, Property property);

    /**
     * add new parameter
     *
     * @param parameter parameter to be added
     * @param parentPropertyId parent property of new parameter
     * @throws IllegalArgumentException when any input is null
     * @throws IllegalStateException when the parameter has id field
     * @throws NotFound when parent property not found
     */
    void addParameter(Parameter parameter, Long parentPropertyId);

    /**
     * get parameter by id
     *
     * @param parameterId requested parameter id
     * @return requested parameter
     * @throws IllegalArgumentException when id is null
     * @throws NotFound when parameter with given id not found
     */
    Parameter getParameterById(Long parameterId);

    /**
     * returns all parameters of the given property
     *
     * @param propertyId propertyId that parameters are bound to
     * @return list of the bounded parameters
     * @throws IllegalArgumentException when propertyId is null
     * @throws NotFound when property with given Id not found
     */
    List<Parameter> getParametersOfProperty(Long propertyId);

    /**
     * delete a parameter
     *
     * @param parameterId parameter id to be deleted
     * @throws IllegalArgumentException when parameter id is null
     * @throws NotFound when parameter with given id not found
     */
    void deleteParameter(Long parameterId);

    /**
     * update a parameter
     *
     * @param parameterId parameter id to be updated
     * @param parameter parameter to be updated
     * @throws IllegalArgumentException when id or parameter input null | id and parameter.id does not match
     * @throws NotFound when parameter with given id not found
     */
    void updateParameter(Parameter parameter, Long parameterId);
}
