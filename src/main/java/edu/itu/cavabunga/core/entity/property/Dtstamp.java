package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Dtstamp extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.DATETIME);
    }
}
