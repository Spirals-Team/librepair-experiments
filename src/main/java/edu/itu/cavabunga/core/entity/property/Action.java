package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.Alarm;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Action extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.TEXT);

        if(this.getValue() != "AUDIO" &&
                this.getValue() != "DISPLAY" &&
                this.getValue() != "EMAIL"){
            throw new Validation("ACTION value is different from acceptable value range: " + this.getValue());
        }
    }
}
