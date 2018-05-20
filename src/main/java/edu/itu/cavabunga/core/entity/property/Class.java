package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.Alarm;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Class extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.TEXT);

        if(this.getValue() != "PUBLIC" &&
                this.getValue() != "PRIVATE" &&
                this.getValue() != "CONFIDENTIAL"){
            throw new Validation("CLASS value is different from acceptable value range: " + this.getValue());
        }
    }
}
