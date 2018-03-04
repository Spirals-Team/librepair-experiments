package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.ComponentProperty;
import org.springframework.stereotype.Component;

@Component
public class ComponentPropertyFactoryImpl implements ComponentPropertyFactory {
    @Override
    public ComponentProperty createComponentProperty(){
        return new ComponentProperty();
    }
}
