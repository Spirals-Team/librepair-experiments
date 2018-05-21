package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Rrule extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.RECUR);
    }
}
