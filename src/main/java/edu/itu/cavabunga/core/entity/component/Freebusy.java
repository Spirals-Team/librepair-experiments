package edu.itu.cavabunga.core.entity.component;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Freebusy extends Component {
    @Override
    public void validate(){
        if(!(this.getParent() instanceof Calendar)){
            throw new Validation("Freebusy component cannot be child of:" + this.getParent().getClass().getName());
        }

        super.validate();

        List<PropertyType> optionalOneList = new ArrayList<PropertyType>();
        optionalOneList.add(PropertyType.Contact);
        optionalOneList.add(PropertyType.Dtend);
        optionalOneList.add(PropertyType.Dtstamp);
        optionalOneList.add(PropertyType.Dtstart);
        optionalOneList.add(PropertyType.Duration);
        optionalOneList.add(PropertyType.Organizer);
        optionalOneList.add(PropertyType.Uid);
        optionalOneList.add(PropertyType.Url);
        super.validateOptionalOneProperties(optionalOneList);
    }
}
