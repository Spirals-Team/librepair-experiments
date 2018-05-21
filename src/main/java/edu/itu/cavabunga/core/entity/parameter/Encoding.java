package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Encoding extends Parameter {
    @Override
    public void validate(){
        super.validate();

        if(this.getValue() != "BASE64" && this.getValue() != "8BIT"){
            throw new Validation("ENCODING value is different from acceptable value range: " + this.getValue());
        }

        for(Parameter pr : this.getProperty().getParameters()){
            if( (pr instanceof Value) && pr.getValue() == "BINARY" && this.getValue() != "BASE64"){
                throw new Validation("While VALUE parameter described as BINARY, ENCODING parameter MUST set as BASE64");
            }
        }
    }
}
