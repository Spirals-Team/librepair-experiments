package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.entity.ComponentProperty;

public interface ComponentPropertyService {
    ComponentProperty createComponentProperty();

    ComponentProperty getComponentPropertyByComponent(edu.itu.cavabunga.core.entity.Component component);

    ComponentProperty getComponentPropertyById(Long id);

    void saveComponentProperty(ComponentProperty componentProperty);

    void deleteComponentProperty(ComponentProperty componentProperty);

    void deleteComponentPropertyById(Long id);
}
