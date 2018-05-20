package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.component.ComponentType;

/**
 * Factory for all property types
 * @see ComponentType
 * @see Component
 */
@org.springframework.stereotype.Component
public class ComponentFactoryImpl implements ComponentFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Component createComponent(ComponentType componentType){ return componentType.create(); }
}
