package tr.edu.itu.cavabunga.server.service;

import tr.edu.itu.cavabunga.lib.entity.Component;
import tr.edu.itu.cavabunga.lib.entity.Parameter;
import tr.edu.itu.cavabunga.lib.entity.Participant;
import tr.edu.itu.cavabunga.lib.entity.Property;
import tr.edu.itu.cavabunga.lib.entity.component.ComponentType;
import tr.edu.itu.cavabunga.lib.entity.parameter.ParameterType;
import tr.edu.itu.cavabunga.lib.entity.property.PropertyType;

import java.util.List;
import java.util.Optional;

/**
 * CRUD service for all ICal elements
 */
public interface IcalService {
    Component createComponent(ComponentType componentType);

    Component createComponentForParticipant(ComponentType componentType, Participant participant);

    /**
     * It returns related component if given id exist
     *
     * @param id id of the component
     * @return related component record or null
     */
    Optional<Component> getComponentById(Long id);

    /**
     * find component which owned by a participant
     *
     * @param owner participant to find component which owned by
     */
    List<Component> getComponentByOwner(Participant owner);

    /**
     * saves given component
     *
     * @param component component record to save
     */
    void saveComponent(Component component);

    /**
     * It deletes record of the given component id
     *
     * @param id component id to delete
     */
    void deleteComponentById(Long id);

    Property createProperty(PropertyType propertyType);

    /**
     * It returns related property if given id exist
     *
     * @param id id of the property
     * @return related property record or null
     */
    Optional<Property>  getPropertyById(Long id);

    /**
     * saves given property
     *
     * @param property property record to save
     */
    void saveProperty(Property property);

    /**
     * It deletes record of the given property id
     *
     * @param id property id to delete
     */
    void deletePropertyById(Long id);

    /**
     *
     * @param parameterType type of parameter to be created
     * @return created parameter
     */
    Parameter createParameter(ParameterType parameterType);

    /**
     * It returns related parameter if given id exist
     *
     * @param id id of the parameter
     * @return related parameter record or null
     */
    Optional<Parameter> getParameterById(Long id);

    /**
     * saves given parameter
     *
     * @param parameter parameter record to save
     */
    void saveParameter(Parameter parameter);

    /**
     * It deletes record of the given parameter id
     *
     * @param id parameter id to delete
     */
    void deleteParameterById(Long id);
}