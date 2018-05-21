package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.Alarm;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Attach extends Property {
    @Override
    public void validate(){
        super.validate();
        try {
            super.validateValueType(PropertyValueType.URI);
        }catch (Exception e){
            super.validateValueType(PropertyValueType.BINARY);
        }
    }
}
