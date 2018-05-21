package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Value extends Parameter{
    @Override
    public void validate(){
        super.validate();
        if(this.getValue() != "BINARY" &&
                this.getValue() != "BOOLEAN" &&
                this.getValue() != "CAL-ADDRESS" &&
                this.getValue() != "DATE-TIME" &&
                this.getValue() != "DATE" &&
                this.getValue() != "DURATION" &&
                this.getValue() != "FLOAT" &&
                this.getValue() != "INTEGER" &&
                this.getValue() != "PERIOD" &&
                this.getValue() != "RECUR" &&
                this.getValue() != "TEXT" &&
                this.getValue() != "TIME" &&
                this.getValue() != "URI" &&
                this.getValue() != "UTC-OFFSET"){
            throw new Validation("VALUE value is different from acceptable value range: " + this.getValue());
        }
    }
}
