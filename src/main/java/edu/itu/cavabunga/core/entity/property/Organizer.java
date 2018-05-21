package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;

import javax.persistence.Entity;

@Entity
public class Organizer extends Property {
    @Override
    public void validate(){
        super.validate();
        super.validateValueType(PropertyValueType.CALADDRESS);
    }
}
