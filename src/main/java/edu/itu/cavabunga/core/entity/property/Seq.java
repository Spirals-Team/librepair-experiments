package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;

import javax.persistence.Entity;

@Entity
public class Seq extends Property {
//TODO: implement SEQUENCE
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.INTEGER);
    }
}
