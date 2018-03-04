package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.component.ComponentType;

public interface ComponentFactory {
    Component createComponent(ComponentType componentType);
}
