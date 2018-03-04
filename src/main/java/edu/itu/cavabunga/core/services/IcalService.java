package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;
import edu.itu.cavabunga.core.entity.property.PropertyType;

import java.util.List;

public interface IcalService {
    Component createComponent(ComponentType componentType);

    Component createComponentForParticipant(ComponentType componentType, Participant participant);

    Component getComponentById(Long id);

    List<Component> getComponentByParent(Component component);

    List<Component> getComponentByParentAndType(Component component, ComponentType componentType);

    List<Component> getComponentByParticipant(Participant participant);

    List<Component> getAllComponentByType(ComponentType componentType);

    List<Component> getComponentByParticipantAndType(Participant participant, ComponentType componentType);

    List<Component> getAllComponent();

    Long countComponentById(Long componentId);

    Long countComponentByIdAndOwnerAndParentIdAndType(Long componentId, Participant owner, Long parentId, ComponentType componentType);

    void saveComponent(Component component);

    void deleteComponentById(Long id);

    void deleteComponent(Component component);

    Property createProperty(PropertyType propertyType);

    Property createPropertyForComponent(PropertyType propertyType, Component component);

    Property getPropertyById(Long id);

    List<Property> getPropertyByComponent(Component component);

    List<Property> getAllPropertyByType(PropertyType propertyType);

    List<Property> getPropertyByComponentAndType(Component component, PropertyType propertyType);

    List<Property> getAllProperty();

    void saveProperty(Property property);

    void deletePropertyById(Long id);

    void deleteProperty(Property property);

    Parameter createParameter(ParameterType parameterType);

    Parameter createParameterForProperty(ParameterType parameterType, Property property);

    Parameter getParameterById(Long id);

    List<Parameter> getParameterByProperty(Property property);

    List<Parameter> getAllParameterByType(ParameterType parameterType);

    List<Parameter> getParameterByPropertyAndType(Property property, ParameterType parameterType);

    List<Parameter> getAllParameter();

    void saveParameter(Parameter parameter);

    void deleteParameterById(Long id);

    void deleteParameter(Parameter parameter);

    Long countParameterByIdAndPropertyId(Long parameterId, Long parentPropertyId);

    Long countParameterById(Long parameterId);

    Long countPropertyByIdAndComponentId(Long propertyId, Long parentComponentId);

    Long countPropertyById(Long propertyId);

    Long countComponentByParentId(Long componentParentId);

    Long countComponentByParentIdAndType(Long parentComponentId, ComponentType componentType);

    Long countComponentByOwnerAndType(Participant owner, ComponentType componentType);

    Long countComponentByIdAndParticipant(Long componetnId, Participant participant);

    Long countPropertyByComponentId(Long parentComponentId);

    Long countParameterByPropertyId(Long parentPropertyId);
}
