package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Transp extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.TEXT);

        if(!this.getValue().equals("OPAQUE") &&
                !this.getValue().equals("TRANSPARENT")){
            throw new Validation("TRANSP property value is out of acceptable range: " + this.getValue());
        }
    }
}
