package tr.edu.itu.cavabunga.server.service;
import java.util.List;
import java.util.Optional;
import tr.edu.itu.cavabunga.lib.entity.Component;
import tr.edu.itu.cavabunga.lib.entity.Parameter;
import tr.edu.itu.cavabunga.lib.entity.Participant;
import tr.edu.itu.cavabunga.lib.entity.Property;
import tr.edu.itu.cavabunga.lib.entity.component.ComponentType;
import tr.edu.itu.cavabunga.lib.entity.parameter.ParameterType;
import tr.edu.itu.cavabunga.lib.entity.property.PropertyType;
import tr.edu.itu.cavabunga.lib.factory.ComponentFactory;
import tr.edu.itu.cavabunga.lib.factory.ParameterFactory;
import tr.edu.itu.cavabunga.lib.factory.PropertyFactory;
import tr.edu.itu.cavabunga.server.repository.ComponentRepository;
import tr.edu.itu.cavabunga.server.repository.ParameterRepository;
import tr.edu.itu.cavabunga.server.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Component> getComponentByOwner(Participant owner){
        return componentRepository.findByOwner(owner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveComponent(Component component){
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