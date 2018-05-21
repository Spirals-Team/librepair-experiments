package edu.itu.cavabunga.core.entity.parameter;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.exception.Validation;
import org.omg.PortableServer.THREAD_POLICY_ID;

import javax.persistence.Entity;

@Entity
public class Range extends Parameter {
    @Override
    public void validate(){
        //TODO:  This parameter can be specified on a property that specifies a recurrence identifier.
        super.validate();

        if(this.getValue() != "THISANDFUTURE"){
            throw new Validation("RANGE parameter can only have THISANDFUTURE value: " + this.getValue());
        }
    }
}
