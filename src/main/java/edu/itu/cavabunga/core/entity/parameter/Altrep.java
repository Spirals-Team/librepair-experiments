package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Altrep extends Parameter {
    @Override
    public void validate(){
        super.validate();
        if(this.getValue().substring(0,1) != "\"" || this.getValue().substring(this.getValue().length() -1) != "\""){
            throw new Validation("ALTREP parameter must start and end with DQUOTE char.");
        }

    }
}
