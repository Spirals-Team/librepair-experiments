package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.entity.ComponentProperty;
import edu.itu.cavabunga.core.factory.ComponentPropertyFactory;
import edu.itu.cavabunga.core.repository.ComponentPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentPropertyServiceImpl implements ComponentPropertyService {
    @Autowired
    private ComponentPropertyFactory componentPropertyFactory;

    @Autowired
    private ComponentPropertyRepository componentPropertyRepository;

    @Override
    public ComponentProperty createComponentProperty(){
        return componentPropertyFactory.createComponentProperty();
    }

    @Override
    public ComponentProperty getComponentPropertyByComponent(edu.itu.cavabunga.core.entity.Component component){
        return componentPropertyRepository.findByComponent(component);
    }

    @Override
    public ComponentProperty getComponentPropertyById(Long id){
        return componentPropertyRepository.findOne(id);
    }

    @Override
    public void saveComponentProperty(ComponentProperty componentProperty){
        componentPropertyRepository.save(componentProperty);
    }

    @Override
    public void deleteComponentProperty(ComponentProperty componentProperty){
        componentPropertyRepository.delete(componentProperty);
    }

    @Override
    public void deleteComponentPropertyById(Long id){
        componentPropertyRepository.delete(id);
    }
}
