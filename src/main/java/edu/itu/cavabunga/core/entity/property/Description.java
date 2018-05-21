package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.Alarm;
import edu.itu.cavabunga.core.entity.component.Event;
import edu.itu.cavabunga.core.entity.component.Journal;
import edu.itu.cavabunga.core.entity.component.Todo;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;
import java.util.concurrent.ExecutionException;

@Entity
public class Description extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.TEXT);
    }
}
