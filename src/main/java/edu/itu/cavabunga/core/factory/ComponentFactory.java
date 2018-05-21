package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.component.ComponentType;

/**
 * Factory for all property types
 * @see ComponentType
 * @see Component
 */
public interface ComponentFactory {

    /**
     * creates component in desired type
     *
     * @param componentType type of the component
     * @return created component object
     */
    Component createComponent(ComponentType componentType);
}
