package edu.itu.cavabunga.business;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.entity.property.PropertyType;

import java.util.List;

public interface CalendarManagerService {
    void addParticipant(Participant participant);

    Participant createParticipant(String userName, ParticipantType participantType);

    Participant getParticipantByUserName(String userName);

    Participant getParticipantById(Long id);

    Participant getParticipantByKey(String userKey);

    List<Participant> getAllParticipantsByType(ParticipantType participantType);

    List<Participant> getAllParticipants();

    void deleteParticipantById(Long id);

    void deleteParticipantByUserName(String userName);

    void updateParticipant(Long id, Participant participant);

    void saveParticipant(Participant participant);

    void addComponent(Component component, String owner, Long parentComponentId);

    Component createComponent(ComponentType componentType);

    Component createComponentForParticipant(ComponentType componentType, String userName);

    Component getComponentById(Long id);

    List<Component> getComponentsByParent(Long parentComponentId);

    List<Component> getComponentsByParentAndType(Long parentComponentId, ComponentType componentType);

    List<Component> getAllComponentsByParticipant(String userName);

    List<Component> getComponentsByParticipantAndType(String userName, ComponentType componentType);

    List<Component> getAllComponentsByType(ComponentType componentType);

    List<Component> getAllComponents();

    void deleteComponentById(Long id);

    void updateComponent(Long id, Component component);

    void saveComponent(Component component);

    void addProperty(Property property, String owner, Long parentComponentId);

    void createProperty(Property property);

    Property createProperty(PropertyType propertyType);

    Property createPropertyForComponent(PropertyType propertyType, Long componentId);

    Property getPropertyById(Long propertyId);

    List<Property> getPropertyForComponent(Long componentId);

    List<Property> getAllPropertiesByType(PropertyType propertyType);

    List<Property> getPropertiesByComponentAndType(PropertyType propertyType, Long componentId);

    List<Property> getAllProperties();

    void deleteProperty(Long propertyId);

    void updateProperty(Long propertyId, Property property);

    void saveProperty(Property property);

    void addParameter(Parameter parameter, String owner, Long parentComponentId, Long parentPropertyId);

    Parameter createParameter(ParameterType parameterType);

    Parameter createParameterForProperty(ParameterType parameterType, Long propertyId);

    Parameter getParameterById(Long parameterId);

    List<Parameter> getParametersByProperty(Long propertyId);

    List<Parameter> getAllParametersByType(ParameterType parameterType);

    List<Parameter> getParameterByPropertyAndType(ParameterType parameterType, Long propertyId);

    List<Parameter> getAllParameter();

    void deleteParameter(Long parameterId);

    void updateParameter(Parameter parameter, Long parameterId);

    void saveParameter(Parameter parameter);


    boolean checkPropertyExistsById(Long propertyId);

    boolean checkComponentExistsById(Long componentId);

    boolean checkParticipantExistsByUserName(String userName);

    boolean checkParticipantExistsById(Long participantId);

    boolean checkComponentChildExistsByParentId(Long componentParentId);

    boolean checkComponentExistsByParentIdAndType(Long parentComponentId, ComponentType componentType);

    boolean checkComponentExistsByParticipantAndType(Participant participant, ComponentType componentType);

    boolean checkComponentExistsByIdAndParticipant(Long componentId, Participant participant);

    boolean checkPropertyExistsByComponentId(Long componentId);

    boolean checkParameterExistsById(Long parameterId);

    boolean checkParameterExistsByParentId(Long parentPropertyId);
}
