package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Fbtype extends Parameter {
    @Override
    public void validate(){
        super.validate();
        if(this.getValue() != "FREE" &&
                this.getValue() != "BUSY" &&
                this.getValue() != "BUSY-UNAVAILABLE" &&
                this.getValue() != "BUSY-TENTATIVE"){
            throw new Validation("FBTYPE value is different from acceptable value range: " + this.getValue());
        }
    }
}
