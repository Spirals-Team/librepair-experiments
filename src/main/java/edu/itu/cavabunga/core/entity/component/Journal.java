package edu.itu.cavabunga.core.entity.component;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.Dtstamp;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.entity.property.PropertyValueType;
import edu.itu.cavabunga.core.entity.property.Uid;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Journal extends Component {
    @Override
    public void validate(){
        if(!(this.getParent() instanceof Component)){
            throw new Validation("Journal component cannot be child of: " + this.getParent().getClass().getName());
        }

        super.validate();

        List<PropertyType> optionalOneList = new ArrayList<PropertyType>();
        optionalOneList.add(PropertyType.Class);
        optionalOneList.add(PropertyType.Created);
        optionalOneList.add(PropertyType.Description);
        optionalOneList.add(PropertyType.Dtstamp);
        optionalOneList.add(PropertyType.Dtstart);
        optionalOneList.add(PropertyType.Lastmod);
        optionalOneList.add(PropertyType.Organizer);
        optionalOneList.add(PropertyType.Recurid);
        optionalOneList.add(PropertyType.Seq);
        optionalOneList.add(PropertyType.Status);
        optionalOneList.add(PropertyType.Summary);
        optionalOneList.add(PropertyType.Uid);
        optionalOneList.add(PropertyType.Url);
        super.validateOptionalOneProperties(optionalOneList);
    }
}
