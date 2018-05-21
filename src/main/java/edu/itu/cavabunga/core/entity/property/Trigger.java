package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Trigger extends Property {
    @Override
    public void validate(){
        super.validate();
        try {
            super.validateValueType(PropertyValueType.DURATION);
        }catch (Validation e){
            super.validateValueType(PropertyValueType.DATETIME);
        }
    }
}
