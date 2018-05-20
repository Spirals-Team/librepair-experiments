package edu.itu.cavabunga.core.entity.component;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Alarm extends Component{
    @Override
    public void validate(){
        if(!(this.getParent() instanceof Event) && !(this.getParent() instanceof Todo)){
            throw new Validation("Alarm component can only be child of Event and Todo, not: " + this.getParent().getClass().getName());
        }

        super.validate();

        List<PropertyType> requireOneList = new ArrayList<PropertyType>();
        requireOneList.add(PropertyType.Action);
        requireOneList.add(PropertyType.Trigger);
        super.validateRequiredOneProperties(requireOneList);

        List<PropertyType> optionalOneList = new ArrayList<PropertyType>();
        optionalOneList.add(PropertyType.Due);
        optionalOneList.add(PropertyType.Repeat);
        super.validateOptionalOneProperties(optionalOneList);
    }
}
