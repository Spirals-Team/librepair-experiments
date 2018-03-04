package edu.itu.cavabunga.business;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.services.IcalService;
import edu.itu.cavabunga.core.services.ParticipantService;
import edu.itu.cavabunga.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarManagerServiceImpl implements CalendarManagerService {
    @Autowired
    private IcalService icalService;

    @Autowired
    private ParticipantService participantService;

    @Override
    public void addParticipant(Participant participant){
         if(participant.getUserName().isEmpty() || participant.getUserName() == null){
             throw new ParticipantConflict(1000,"Participant username cannot be empty");
         }

         if(participant.getId() != null){
             throw new  ParticipantConflict(10001,"New participant cannot have id field, please use update methods");
         }

         //Participant checkParticipant = participantService.getParticipantByUserName(participant.getUserName());
         if(checkParticipantExistsByUserName(participant.getUserName())){
             throw new  ParameterConflict(10002, "Participant with username: " + participant.getUserName() + " is already exists");
         }

         participantService.saveParticipant(participant);
    }

    @Override
    public Participant createParticipant(String userName, ParticipantType participantType){
        //Participant checkParticipant = participantService.getParticipantByUserName(userName);
        if(checkParticipantExistsByUserName(userName)){
            throw new ParticipantConflict(1001, "Participant with username: " + userName + " is already exists");
        }

        return participantService.createParticipant(userName, participantType);
    }

    @Override
    public Participant getParticipantByUserName(String userName){
        if(!checkParticipantExistsByUserName(userName)){
            throw new ParticipantNotFound(1002,"Participant with username: " + userName + " couldn't found");
        }

        return participantService.getParticipantByUserName(userName);
    }

    @Override
    public Participant getParticipantById(Long id){
        //Participant checkParticipant =
        if(!checkComponentExistsById(id)){
            throw new ParticipantNotFound(1003, "Participant with id: " + id + " couldn't found");
        }

        return participantService.getParticipantById(id);
    }

    @Override
    public Participant getParticipantByKey(String userKey){
        try{
            return getParticipantByUserName(userKey);
        }catch (Exception e){
            Long convert2Id;
            try {
                convert2Id = Long.valueOf(userKey);
            }catch (NumberFormatException f){
                throw new ParticipantConflict(50001,"Userkey input cannot convert to Long number");
            }

            return getParticipantById(convert2Id);
        }
    }

    @Override
    public List<Participant> getAllParticipantsByType(ParticipantType participantType){
        List<Participant> participants = participantService.getAllParticipantByType(participantType);
        if(participants.isEmpty()){
            throw new ParticipantNotFound(1004 ,"No participants in type of: " + participantType.toString() + " found");
        }

        return participants;
    }

    @Override
    public List<Participant> getAllParticipants(){
        List<Participant> participants = participantService.getAllParticipant();
        if(participants.isEmpty()){
            throw new ParticipantNotFound(1005,"No participant found");
        }

        return participants;
    }

    @Override
    public void deleteParticipantById(Long id){
        //Participant checkParticipant = participantService.getParticipantById(id);
        if(!checkParticipantExistsById(id)){
            throw new ParticipantNotFound(1006,"Participant with id: " + id + " couldn't found");
        }

        participantService.deleteParticipantById(id);
    }

    @Override
    public void deleteParticipantByUserName(String userName){
        //Participant checkParticipant = participantService.getParticipantByUserName(userName);
        if(!checkParticipantExistsByUserName(userName)){
            throw new ParticipantNotFound(1007,"Participant with username: " + userName + " couldn't found");
        }

        participantService.deleteParticipantByUserName(userName);
    }

    @Override
    public void updateParticipant(Long id, Participant participant){
        //Participant checkParticipant = participantService.getParticipantById(id);
        if(!checkParticipantExistsById(id)){
            throw new ParticipantNotFound(1008,"Participant with id: " + id + " couldn't found");
        }

        if((participant.getId() != id) && (participant.getId() != null)){
            throw new ParticipantConflict(1009, "Participant data has id: " + participant.getId() + " but update id: " + id);
        }

        participant.setId(id);
        participantService.saveParticipant(participant);
    }

    @Override
    public void saveParticipant(Participant participant){
        participantService.saveParticipant(participant);
    }

    @Override
    public void addComponent(Component component, String owner, Long parentComponentId){
        if(owner.isEmpty() || owner == null){
            throw new ComponentConflict(20001, "Owner of a component can't be empty");
        }

        //Participant checkParticipant = participantService.getParticipantByUserName(owner);
        if(!checkParticipantExistsByUserName(owner)){
            throw new ParticipantNotFound(20002,"Participant with username: " + owner + " couldn't found");
        }

        //Component checkComponent = icalService.getComponentById(parentComponentId);
        if(!checkComponentExistsById(parentComponentId) && parentComponentId != null){
            throw new ComponentNotFound(20003,"Parent component with id: " + parentComponentId + " couldn't found");
        }

        component.setOwner(participantService.getParticipantByUserName(owner));
        if(parentComponentId == null){
            component.setParent(null);
        }else{
            component.setParent(icalService.getComponentById(parentComponentId));
        }
        icalService.saveComponent(component);
    }

    @Override
    public Component createComponent(ComponentType componentType){
        return icalService.createComponent(componentType);
    }

    @Override
    public Component createComponentForParticipant(ComponentType componentType, String userName){
        //Participant checkParticipant = participantService.getParticipantByUserName(userName);
        if(!checkParticipantExistsByUserName(userName)){
            throw new ParticipantNotFound(1010,"Participant with userName: " + userName + " couldn't found");
        }

        return icalService.createComponentForParticipant(componentType, participantService.getParticipantByUserName(userName));
    }

    @Override
    public Component getComponentById(Long id){
        //Component checkComponent = icalService.getComponentById(id);
        if(!checkComponentExistsById(id)){
            throw new ComponentNotFound(1011,"Component with id: " + id + " couldn't found");
        }

        return icalService.getComponentById(id);
    }

    @Override
    public List<Component> getComponentsByParent(Long parentComponentId){
        //Component checkParent = icalService.getComponentById(parentComponentId);
        if(!checkComponentExistsById(parentComponentId)){
            throw new ComponentNotFound(1012,"Parent component with id: " + parentComponentId + " couldn't found");
        }


        //List<Component> checkComponents = icalService.getComponentByParent(checkParent);
        if(!checkComponentChildExistsByParentId(parentComponentId)){
            throw new ComponentNotFound(1013,"No child component found for parent id: " + parentComponentId);
        }

        return icalService.getComponentByParent(icalService.getComponentById(parentComponentId));
    }

    @Override
    public List<Component> getComponentsByParentAndType(Long parentComponentId, ComponentType componentType){
        //Component checkParent = icalService.getComponentById(parentComponentId);
        if(!checkComponentExistsById(parentComponentId)){
            throw new ComponentNotFound(1014,"Parent component with id: " + parentComponentId + " couldn't found");
        }

        //List<Component> checkComponents = icalService.getComponentByParentAndType(checkParent, componentType);
        if(!checkComponentExistsByParentIdAndType(parentComponentId,componentType)){
            throw new ComponentNotFound(1015,"No child component found for parent id: " + parentComponentId + " and type of " + componentType.toString());
        }

        return icalService.getComponentByParentAndType(icalService.getComponentById(parentComponentId), componentType);
    }

    @Override
    public List<Component> getAllComponentsByParticipant(String userName){
        Participant checkParticipant = participantService.getParticipantByUserName(userName);
        if(checkParticipant == null){
            throw new ParticipantNotFound(1016,"Participant with username: " + userName + " couldn't found");
        }

        List<Component> checkComponents = icalService.getComponentByParticipant(checkParticipant);
        if(checkComponents.isEmpty()){
            throw new ComponentNotFound(1017,"No component found for participant: " + userName);
        }

        return checkComponents;
    }

    @Override
    public List<Component> getComponentsByParticipantAndType(String userName, ComponentType componentType){
        //Participant checkParticipant = participantService.getParticipantByUserName(userName);
        if(!checkParticipantExistsByUserName(userName)){
            throw new ParticipantNotFound(1018,"Participant with username: " + userName + " couldn't found");
        }

        //List<Component> checkComponents = icalService.getComponentByParticipantAndType(checkParticipant, componentType);
        if(!checkComponentExistsByParticipantAndType(participantService.getParticipantByUserName(userName), componentType)){
            throw new ComponentNotFound(1019,"No component found in type of: " + componentType.toString() + " for participant username: " + userName);
        }

        return icalService.getComponentByParticipantAndType(participantService.getParticipantByUserName(userName), componentType);
    }

    @Override
    public List<Component> getAllComponentsByType(ComponentType componentType){
        List<Component> components = icalService.getAllComponentByType(componentType);
        if(components.isEmpty()){
            throw new ComponentNotFound(1020,"No component founf in type of: " + componentType.toString());
        }

        return components;
    }

    @Override
    public List<Component> getAllComponents(){
        List<Component> components = icalService.getAllComponent();
        if(components.isEmpty()){
            throw new ComponentNotFound(1021, "No component found");
        }

        return components;
    }

    @Override
    public void deleteComponentById(Long id){
        //Component checkComponent = icalService.getComponentById(id);
        if(!checkComponentExistsById(id)){
            throw new ComponentNotFound(1022, "Component with id: " + id + " couldn't found");
        }

        icalService.deleteComponentById(id);
    }

    @Override
    public void updateComponent(Long id, Component component){
        //Component checkComponent = icalService.getComponentById(id);
        if(!checkComponentExistsById(id)){
            throw new ComponentNotFound(1023,"Component with id: " + id + " couldn't found");
        }

        if((component.getId() != null) && (component.getId() != id)){
            throw new ComponentConflict(1024, "Participant data has id: " + component.getId() + " but update id: " + id);
        }

        component.setId(id);
        saveComponent(component);
    }

    @Override
    public void saveComponent(Component component){
        icalService.saveComponent(component);
    }

    @Override
    public void addProperty(Property property, String owner, Long parentComponentId){
        //Participant checkParticipant = participantService.getParticipantByUserName(owner);
        if(!checkParticipantExistsByUserName(owner)){
            throw new ParticipantNotFound(30001,"Participant with username: " + owner + " couldn't found");
        }

        //Component checkComponent = icalService.getComponentById(parentComponentId);
        if(!checkComponentExistsById(parentComponentId)){
            throw new ComponentNotFound(30002,"Component with parent id: " + parentComponentId + " couldn't found");
        }

        if(!checkComponentExistsByIdAndParticipant(parentComponentId, participantService.getParticipantByUserName(owner))){
            throw new ComponentConflict(30003,"Parent component owner is different from what send: " + owner);
        }

        if(property.getId() != null){
            throw new PropertyConflict(30004,"New property cannot have id field, please use update methods");
        }

        property.setComponent(icalService.getComponentById(parentComponentId));
        icalService.saveProperty(property);
    }

    @Override
    public void createProperty(Property property){
    }

    @Override
    public Property createProperty(PropertyType propertyType){
        return icalService.createProperty(propertyType);
    }

    @Override
    public Property createPropertyForComponent(PropertyType propertyType, Long componentId){
        //Component checkComponent = icalService.getComponentById(componentId);
        if(!checkComponentExistsById(componentId)){
            throw new ComponentNotFound(1025,"Component with id:" + componentId + " couldn't found");
        }

        return  icalService.createPropertyForComponent(propertyType, icalService.getComponentById(componentId));
    }

    @Override
    public Property getPropertyById(Long propertyId){
        //Property checkProperty =
        if(!checkPropertyExistsById(propertyId)){
            throw new PropertyNotFound(1026,"Property with id: " + propertyId + "cannot found");
        }

        return icalService.getPropertyById(propertyId);
    }

    @Override
    public List<Property> getPropertyForComponent(Long componentId){
        //Component checkComponent = icalService.getComponentById(componentId);
        if(!checkComponentExistsById(componentId)){
            throw new ComponentNotFound(1027,"Component with id: " + componentId + " couldn't found");
        }

        //List<Property> properties = icalService.getPropertyByComponent(checkComponent);
        if(!checkPropertyExistsByComponentId(componentId)){
            throw new PropertyNotFound(1028,"No property found for property parent id: " + componentId);
        }

        return icalService.getPropertyByComponent(icalService.getComponentById(componentId));
    }

    @Override
    public List<Property> getAllPropertiesByType(PropertyType propertyType){
        List<Property> properties = icalService.getAllPropertyByType(propertyType);
        if(properties.isEmpty()){
            throw new PropertyNotFound(1029,"No property found in type of: " + propertyType.toString());
        }

        return properties;
    }

    @Override
    public List<Property> getPropertiesByComponentAndType(PropertyType propertyType, Long componentId){
        //Component checkComponent = icalService.getComponentById(componentId);
        if(!checkComponentExistsById(componentId)){
            throw new ComponentNotFound(1030,"Component with id: " + componentId + " couldnt found");
        }

        List<Property> properties = icalService.getPropertyByComponentAndType(icalService.getComponentById(componentId), propertyType);
        if(properties.isEmpty()){
            throw new PropertyNotFound(1031,"No property found for parent component id: " + componentId + " in type of " + propertyType.toString());
        }

        return properties;
    }

    @Override
    public List<Property> getAllProperties(){
        List<Property> properties = icalService.getAllProperty();
        if(properties.isEmpty()){
            throw new PropertyNotFound(1032,"No property found");
        }

        return properties;
    }

    @Override
    public void deleteProperty(Long propertyId){
        //Property checkProperty = icalService.getPropertyById(propertyId);
        if(!checkPropertyExistsById(propertyId)){
            throw new PropertyNotFound(1033,"Property with id: " + propertyId + " couldnt find");
        }

        icalService.deletePropertyById(propertyId);
    }

    @Override
    public void updateProperty(Long propertyId, Property property){
        //Property checkProperty = icalService.getPropertyById(propertyId);
        if(!checkPropertyExistsById(propertyId)){
            throw new PropertyNotFound(1034,"Property with id " + propertyId + " couldnt find");
        }

        if((property.getId() != null) && (property.getId() != propertyId)){
            throw new PropertyConflict(1035,"Property data has id: " + property.getId() + " but update id: " + propertyId);
        }

        property.setId(propertyId);
        icalService.saveProperty(property);
    }

    @Override
    public void saveProperty(Property property){
        icalService.saveProperty(property);
    }

    @Override
    public void addParameter(Parameter parameter, String owner, Long parentComponentId, Long parentPropertyId){
        //Participant checkParticipant = participantService.getParticipantByUserName(owner);
        if(!checkParticipantExistsByUserName(owner)){
            throw new ParticipantNotFound(40001,"Participant with username: " + owner + " couldn't found");
        }

        //Component checkComponent = icalService.getComponentById(parentComponentId);
        if(!checkComponentExistsById(parentComponentId)){
            throw new ComponentNotFound(40002,"Component with id: " + parentComponentId + " couldn't found");
        }

        //Property checkProperty = icalService.getPropertyById(parentPropertyId);
        if(!checkPropertyExistsById(parentPropertyId)){
            throw new PropertyNotFound(40003,"Property with id: " + parentPropertyId + " couldn't found");
        }

        if(parameter.getId() != null){
            throw new  ParameterConflict(40006,"New paremeter cannot have id field, please use update methods");
        }

        parameter.setProperty(icalService.getPropertyById(parentPropertyId));
        icalService.saveParameter(parameter);
    }

    @Override
    public Parameter createParameter(ParameterType parameterType){
        return icalService.createParameter(parameterType);
    }

    @Override
    public Parameter createParameterForProperty(ParameterType parameterType, Long propertyId){
        //Property checkProperty = icalService.getPropertyById(propertyId);
        if(!checkPropertyExistsById(propertyId)){
            throw new PropertyNotFound(1036,"Property for id: " + propertyId + " couldnt find");
        }

        return icalService.createParameterForProperty(parameterType, icalService.getPropertyById(propertyId));
    }

    @Override
    public Parameter getParameterById(Long parameterId){
        //Parameter checkParameter = icalService.getParameterById(parameterId);
        if(!checkParameterExistsById(parameterId)){
            throw new ParameterNotFound(1037,"Parameter for id: " + parameterId +  " couldn't find");
        }

        return icalService.getParameterById(parameterId);
    }

    @Override
    public List<Parameter> getParametersByProperty(Long propertyId){
        //Property checkProperty = icalService.getPropertyById(propertyId);
        if(!checkPropertyExistsById(propertyId)){
            throw new PropertyNotFound(1038,"Property for id: " + propertyId +  "couldn't find");
        }

        //List<Parameter> parameters = icalService.getParameterByProperty(checkProperty);
        if(!checkParameterExistsByParentId(propertyId)){
            throw new ParameterNotFound(1039,"No parameter found for parent property id: " + propertyId);
        }

        return icalService.getParameterByProperty(icalService.getPropertyById(propertyId));
    }

    @Override
    public List<Parameter> getAllParametersByType(ParameterType parameterType){
        List<Parameter> checkParameter = icalService.getAllParameterByType(parameterType);
        if(checkParameter.isEmpty()){
            throw new ParameterNotFound(1040,"No parameter found");
        }

        return checkParameter;
    }

    @Override
    public List<Parameter> getParameterByPropertyAndType(ParameterType parameterType, Long propertyId){
        Property checkProperty = icalService.getPropertyById(propertyId);
        if(checkProperty == null){
            throw new PropertyNotFound(1041, "Property with id: " + propertyId + " couldnt find");
        }

        List<Parameter> parameters = icalService.getParameterByPropertyAndType(checkProperty, parameterType);
        if(parameters.isEmpty()){
            throw new ParameterNotFound(1042,"No parameter found for parent property id: " + propertyId + " in type of " + parameterType);
        }

        return parameters;
    }

    @Override
    public List<Parameter> getAllParameter(){
        List<Parameter> checkParameters = icalService.getAllParameter();
        if(checkParameters.isEmpty()){
            throw new ParameterNotFound(1043,"No parameter found");
        }

        return checkParameters;
    }

    @Override
    public void deleteParameter(Long parameterId){
        //Parameter parameter = icalService.getParameterById(parameterId);
        if(!checkParameterExistsById(parameterId)){
            throw new ParameterNotFound(1044,"Parameter with id: " + parameterId + " couldn't found");
        }

        icalService.deleteParameterById(parameterId);
    }

    @Override
    public void updateParameter(Parameter parameter, Long parameterId){
        //Parameter checkParameter = icalService.getParameterById(parameterId);
        if(!checkParameterExistsById(parameterId)){
            throw new ParameterNotFound(1045,"Parameter with id: " + parameterId + " not found");
        }

        if((parameter.getId() != null) && (parameter.getId() != parameterId)){
            throw new ParameterConflict(1046,"Parameter data has id: " + parameter.getId() + " but update id: " + parameterId);
        }

        parameter.setId(parameterId);
        icalService.saveParameter(parameter);
    }

    @Override
    public void saveParameter(Parameter parameter){
        icalService.saveParameter(parameter);
    }


    @Override
    public boolean checkParticipantExistsById(Long participantId){
        if(participantService.countParticipantById(participantId) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkParticipantExistsByUserName(String userName){
        if(participantService.countParticipantByUserName(userName) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkComponentExistsById(Long componentId){
        if(icalService.countComponentById(componentId) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkPropertyExistsById(Long propertyId){
        if(icalService.countPropertyById(propertyId) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkComponentChildExistsByParentId(Long componentParentId){
        if(icalService.countComponentByParentId(componentParentId) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkComponentExistsByParentIdAndType(Long parentComponentId, ComponentType componentType){
        if(icalService.countComponentByParentIdAndType(parentComponentId,componentType) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkComponentExistsByParticipantAndType(Participant participant, ComponentType componentType){
        if(icalService.countComponentByOwnerAndType(participant, componentType) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkComponentExistsByIdAndParticipant(Long componentId, Participant participant){
        if(icalService.countComponentByIdAndParticipant(componentId,participant) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkPropertyExistsByComponentId(Long componentId){
        if(icalService.countPropertyByComponentId(componentId) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkParameterExistsById(Long parameterId){
        if(icalService.countParameterById(parameterId) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkParameterExistsByParentId(Long parentPropertyId){
        if(icalService.countParameterByPropertyId(parentPropertyId) < 1){
            return false;
        }

        return true;
    }
}
