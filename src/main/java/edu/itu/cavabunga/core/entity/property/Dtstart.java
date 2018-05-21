package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Dtstart extends Property {
    @Override
    public void validate(){
        super.validate();
        try {
            super.validateValueType(PropertyValueType.DATETIME);
        }catch (Validation e){
            super.validateValueType(PropertyValueType.DATE);
        }
    }
}
