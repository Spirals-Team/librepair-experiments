package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.Alarm;
import edu.itu.cavabunga.core.entity.component.Calendar;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Calscale extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.TEXT);

        //TODO: is cavabunga support only for Gregorian calendar?
        if(this.getValue() != "GREGORIAN" ){
            throw new Validation("CALSCALE value is different from acceptable value range: " + this.getValue());
        }
    }
}
