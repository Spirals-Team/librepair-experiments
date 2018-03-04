package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.PropertyType;

public interface PropertyFactory {
    Property createProperty(PropertyType propertyType);
}
