package edu.itu.cavabunga.core.service;
import java.util.Optional;
import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.factory.ComponentFactory;
import edu.itu.cavabunga.core.factory.ParameterFactory;
import edu.itu.cavabunga.core.factory.PropertyFactory;
import edu.itu.cavabunga.core.repository.ComponentRepository;
import edu.itu.cavabunga.core.repository.ParameterRepository;
import edu.itu.cavabunga.core.repository.PropertyRepository;
import edu.itu.cavabunga.exception.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * {@inheritDoc}
 */
@Service
public class IcalServiceImpl implements IcalService {
    private ComponentFactory componentFactory;

    private ComponentRepository componentRepository;

    private PropertyFactory propertyFactory;

    private PropertyRepository propertyRepository;

    private ParameterFactory parameterFactory;

    private ParameterRepository parameterRepository;

    /**
     * constructor for dependency injection
     *
     * @param componentFactory to inject ComponentFactory
     * @param componentRepository to inject ComponentRepository
     * @param propertyFactory to inject PropertyFactory
     * @param propertyRepository to inject PropertyRepository
     * @param parameterFactory to inject ParameterFactory
     * @param parameterRepository to inject ParameterRepository
     */
    @Autowired
    public IcalServiceImpl(
        ComponentFactory componentFactory,
        ComponentRepository componentRepository,
        PropertyFactory propertyFactory,
        PropertyRepository propertyRepository,
        ParameterFactory parameterFactory,
        ParameterRepository parameterRepository
    ) {
        this.componentFactory = componentFactory;
        this.componentRepository = componentRepository;
        this.propertyFactory = propertyFactory;
        this.propertyRepository = propertyRepository;
        this.parameterFactory = parameterFactory;
        this.parameterRepository = parameterRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component createComponent(ComponentType componentType){
        return componentFactory.createComponent(componentType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component createComponentForParticipant(ComponentType componentType, Participant participant){
        Component result = componentFactory.createComponent(componentType);
        result.setOwner(participant);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Component> getComponentById(Long id){
        return componentRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveComponent(Component component){
        try {
            component.validate();
        }catch (Exception e){
            throw new Validation("component couldnor send to repository, validation failed: " + e.getMessage());
        }

        componentRepository.save(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteComponentById(Long id){
        componentRepository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Property createProperty(PropertyType propertyType){
        return  propertyFactory.createProperty(propertyType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Property> getPropertyById(Long id){
        return propertyRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveProperty(Property property){
        try {
            property.validate();
        }catch (Exception e){
            throw new Validation("Propererty couldnot send reposiyory, validation failed: " + e.getMessage());
        }

        propertyRepository.save(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePropertyById(Long id){
        propertyRepository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parameter createParameter(ParameterType parameterType){
        return parameterFactory.createParameter(parameterType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Parameter> getParameterById(Long id){
        return parameterRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveParameter(Parameter parameter){
        try {
            parameter.validate();
        }catch (Exception e){
            throw new Validation("Parameter couldnot send to repository, validation failed: " + e.getMessage());

        }

        parameterRepository.save(parameter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteParameterById(Long id){
        parameterRepository.delete(id);
    }
}
