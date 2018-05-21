package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Percent extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.INTEGER);

        if(Integer.parseInt(this.getValue()) > 0 | Integer.parseInt(this.getValue()) < 0){
            throw new Validation("PERCENT property must be in range of 0 to 100: " + this.getValue());
        }
    }
}
