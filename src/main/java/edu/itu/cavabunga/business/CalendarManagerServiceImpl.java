package edu.itu.cavabunga.business;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.service.IcalService;
import edu.itu.cavabunga.core.service.ParticipantService;
import edu.itu.cavabunga.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class CalendarManagerServiceImpl implements CalendarManagerService {

    private IcalService icalService;

    private ParticipantService participantService;

    @Autowired
    public CalendarManagerServiceImpl(IcalService icalService, ParticipantService participantService) {
        this.icalService = icalService;
        this.participantService = participantService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addParticipant(Participant participant){
        Assert.notNull(participant.getUserName(), "Participant must not be null!");
        Assert.state(
                participant.getId() == null,
                "New participant cannot have id field, please use update methods"
        );

        if(participantService.getParticipantByUserName(participant.getUserName()).isPresent()) {
            throw new Conflict("Participant with username: " + participant.getUserName() + " is already exist.");
        }

        participantService.saveParticipant(participant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Participant getParticipantByUserName(String userName){
        Assert.notNull(userName, "Username must not be null!");

        if(!participantService.getParticipantByUserName(userName).isPresent()) {
            throw new NotFound("Participant with username: " + userName + " couldn't found");
        }

        return participantService.getParticipantByUserName(userName).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Participant> getAllParticipants(){
        return participantService.getAllParticipant();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteParticipantById(Long id){
        Assert.notNull(id, "");

        if(!participantService.getParticipantById(id).isPresent()) {
            throw new NotFound("Participant with id: " + id + " couldn't found");
        }

        participantService.deleteParticipantById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateParticipant(Long id, Participant participant){
        Assert.notNull(id, "Id must not be null!");
        Assert.notNull(participant, "Participant must not be null!");
        Assert.isTrue(id.equals(participant.getId()), "ID doesn't match!");

        if(!participantService.getParticipantById(id).isPresent()) {
            throw new NotFound("Participant with id: " + id + " couldn't found");
        }

        participantService.saveParticipant(participant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComponent(Component component, String owner, Long parentComponentId){
        Assert.notNull(component, "Component must not be null!");
        Assert.notNull(owner, "Username must not be null!");
        Assert.notNull(parentComponentId, "ParentComponentId must not be null!");

        Assert.state(
                component.getId() == null,
                "44New component cannot have id field, please use update methods"
        );

        if(!participantService.getParticipantByUserName(owner).isPresent()) {
            throw new NotFound("owner: " + owner + " couldn't found");
        }
        if(!icalService.getComponentById(parentComponentId).isPresent()) {
            throw new NotFound("Parent component not found");
        }

        component.setOwner(participantService.getParticipantByUserName(owner).get());
        component.setParent(icalService.getComponentById(parentComponentId).get());
        icalService.saveComponent(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getComponentById(Long id){
        Assert.notNull(id, "Id must not be null!");

        if(!icalService.getComponentById(id).isPresent()) {
            throw new NotFound("Component with ID: " + id + " couldn't found");
        }

        return icalService.getComponentById(id).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteComponentById(Long id){
        Assert.notNull(id, "Id must not be null!");

        if(!icalService.getComponentById(id).isPresent()) {
            throw new NotFound("Component with ID: " + id + " couldn't found");
        }

        icalService.deleteComponentById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateComponent(Long id, Component component){
        Assert.notNull(id, "Id must not be null!");
        Assert.notNull(component, "Component must not be null!");
        Assert.isTrue(id.equals(component.getId()), "ID doesn't match!");

        if(!icalService.getComponentById(id).isPresent()) {
            throw new NotFound("Component with ID: " + id + " couldn't found");
        }

        icalService.saveComponent(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProperty(Property property, Long parentComponentId){
        Assert.notNull(property, "Property must not be null!");
        Assert.notNull(parentComponentId, "ParentComponentId must not be null!");
        Assert.state(
                property.getId() == null,
                "55New Property cannot have id field, please use update methods"
        );

        if(!icalService.getComponentById(parentComponentId).isPresent()) {
            throw new NotFound("Parent component not found");
        }

        property.setComponent(icalService.getComponentById(parentComponentId).get());
        icalService.saveProperty(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property getPropertyById(Long propertyId){
        Assert.notNull(propertyId, "Id must not be null!");

        if(!icalService.getPropertyById(propertyId).isPresent()) {
            throw new NotFound("Property with ID: " + propertyId + " couldn't found");
        }

        return icalService.getPropertyById(propertyId).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Property> getPropertiesOfComponent(Long componentId){
        Assert.notNull(componentId, "Id must not be null!");

        if(!icalService.getComponentById(componentId).isPresent()) {
            throw new NotFound("Component with ID: " + componentId + " couldn't found");
        }
        return icalService.getComponentById(componentId).get().getProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProperty(Long propertyId){
        Assert.notNull(propertyId, "Id must not be null!");

        if(!icalService.getComponentById(propertyId).isPresent()) {
            throw new NotFound("Property with ID: " + propertyId + " couldn't found");
        }

        icalService.deletePropertyById(propertyId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProperty(Long propertyId, Property property){
        Assert.notNull(propertyId, "Id must not be null!");
        Assert.notNull(property, "Property must not be null!");
        Assert.state(propertyId.equals(property.getId()), "ID doesn't match!");

        if(!icalService.getComponentById(propertyId).isPresent()) {
            throw new NotFound("Property with ID: " + propertyId + " couldn't found");
        }

        icalService.saveProperty(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addParameter(Parameter parameter, Long parentPropertyId){
        Assert.notNull(parameter, "Parameter must not be null!");
        Assert.notNull(parentPropertyId, "ParentPropertyId must not be null!");
        Assert.state(
                parameter.getId() == null,
                "77New parameter cannot have id field, please use update methods"
        );

        if(!icalService.getPropertyById(parentPropertyId).isPresent()) {
            throw new NotFound("Parent property not found");
        }
        parameter.setProperty(icalService.getPropertyById(parentPropertyId).get());
        icalService.saveParameter(parameter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parameter getParameterById(Long parameterId){
        Assert.notNull(parameterId, "Id must not be null!");

        if(!icalService.getParameterById(parameterId).isPresent()) {
            throw new NotFound("Parameter with ID: " + parameterId + " couldn't found");
        }

        return icalService.getParameterById(parameterId).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Parameter> getParametersOfProperty(Long propertyId){
        Assert.notNull(propertyId, "Id must not be null!");

        if(!icalService.getPropertyById(propertyId).isPresent()) {
            throw new NotFound("Property with ID: " + propertyId + " couldn't found");
        }
        return icalService.getPropertyById(propertyId).get().getParameters();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteParameter(Long parameterId){
        Assert.notNull(parameterId, "Id must not be null!");

        if(!icalService.getParameterById(parameterId).isPresent()) {
            throw new NotFound("Parameter with ID: " + parameterId + " couldn't found");
        }

        icalService.deleteParameterById(parameterId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateParameter(Parameter parameter, Long parameterId){
        Assert.notNull(parameter, "parameter must not be null!");
        Assert.notNull(parameterId, "parameterId must not be null!");
        Assert.state(parameterId.equals(parameter.getId()), "ID doesn't match!");

        if(!icalService.getParameterById(parameterId).isPresent()) {
            throw new NotFound("Parameter with ID: " + parameterId + " couldn't found");
        }

        icalService.saveParameter(parameter);
    }
}
