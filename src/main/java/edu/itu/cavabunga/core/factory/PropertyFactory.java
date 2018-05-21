package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.PropertyType;

/**
 * Factory interface for all property types
 * @see PropertyType
 * @see Property
 */
public interface PropertyFactory {

    /**
     * creates property in desired type
     *
     * @param propertyType type of the property
     * @return created property object
     */
    Property createProperty(PropertyType propertyType);
}
