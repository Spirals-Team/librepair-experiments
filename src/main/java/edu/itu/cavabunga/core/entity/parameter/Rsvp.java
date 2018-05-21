package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Rsvp extends Parameter {
    @Override
    public void validate(){
        //TODO: This parameter can be specified on properties with a CAL-ADDRESS value type.
        super.validate();
        if(this.getValue() != "TRUE" &&
                this.getValue() != "FALSE"){
            throw new Validation("RSVP value is different from acceptable value range: " + this.getValue());
        }
    }
}
