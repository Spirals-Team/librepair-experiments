package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.component.Event;
import edu.itu.cavabunga.core.entity.component.Journal;
import edu.itu.cavabunga.core.entity.component.Todo;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;

@Entity
public class Partstat extends Parameter {
    @Override
    public void validate(){
        //TODO: This parameter can be specified on properties with a CAL-ADDRESS value type
        super.validate();

        if(this.getProperty().getComponent() instanceof Event){
            if(this.getValue() != "NEEDS-ACTION" &&
                    this.getValue() != "ACCEPTED" &&
                    this.getValue() != "DECLINED" &&
                    this.getValue() != "TENTATIVE" &&
                    this.getValue() != "DELEGATED"){
                throw new Validation("PARTSTAT value is different from acceptable value range in an Event component: " + this.getValue());
            }
        }

        if(this.getProperty().getComponent() instanceof Todo){
            if(this.getValue() != "NEEDS-ACTION" &&
                    this.getValue() != "ACCEPTED" &&
                    this.getValue() != "DECLINED" &&
                    this.getValue() != "TENTATIVE" &&
                    this.getValue() != "DELEGATED" &&
                    this.getValue() != "COMPLETED" &&
                    this.getValue() != "IN-PROCESS"){
                throw new Validation("PARTSTAT value is different from acceptable value range in a Todo component: " + this.getValue());
            }
        }

        if(this.getProperty().getComponent() instanceof Journal){
            if(this.getValue() != "NEEDS-ACTION" &&
                    this.getValue() != "ACCEPTED" &&
                    this.getValue() != "DECLINED"){
                throw new Validation("PARTSTAT value is different from acceptable value range in a Journal component " + this.getValue());
            }
        }
    }
}
