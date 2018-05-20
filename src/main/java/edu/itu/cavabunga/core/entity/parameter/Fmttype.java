package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Fmttype extends Parameter {
    @Override
    public void validate(){
        //TODO: This parameter can be specified on properties that are used to reference an object
        super.validate();
        if(this.getValue().matches("(\\w+)/(\\w+)") != true){
            throw new Validation("FMTYPE must have valid mime type: " + this.getValue());
        }
    }
}
