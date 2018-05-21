package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.component.Calendar;
import edu.itu.cavabunga.core.entity.property.*;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Tzid extends Parameter {
    @Override
    public void validate(){
        super.validate();
        if(!(this.getProperty() instanceof Dtstart) &&
                !(this.getProperty() instanceof Dtend) &&
                !(this.getProperty() instanceof Due) &&
                !(this.getProperty() instanceof Exdate) &&
                !(this.getProperty() instanceof Rdate)){
            throw new Validation("TZID parameter can only be use in Dtstart, Dtend, Due, Exdate and Rdate properties: " + this.getProperty().getClass().getName());
        }

        //TODO: Validate TZID string matches with the Calendar's VTIMEZONE component's TZID identifier


    }
}
