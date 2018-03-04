package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.component.ComponentType;

@org.springframework.stereotype.Component
public class ComponentFactoryImpl implements ComponentFactory {
    @Override
    public Component createComponent(ComponentType componentType){ return componentType.create(); }
}
