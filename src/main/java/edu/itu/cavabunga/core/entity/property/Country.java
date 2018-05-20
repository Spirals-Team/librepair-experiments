package edu.itu.cavabunga.core.entity.property;

import edu.itu.cavabunga.core.entity.Property;

import javax.persistence.Entity;

@Entity
public class Country extends Property {
    //TODO: check rfc5545 to confirm if it exists
    @Override
    public void validate(){

    }
}
