package edu.itu.cavabunga.core.entity.component;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Daylight extends Component{
    @Override
    public void validate(){
        if(!(this.getParent() instanceof Timezone)){
            throw new Validation("Daylight component cannot be child of :" + this.getParent().getClass().getName());
        }

        super.validate();

        List<PropertyType> requireOneList = new ArrayList<PropertyType>();
        requireOneList.add(PropertyType.Dtstart);
        requireOneList.add(PropertyType.Tzname);
        requireOneList.add(PropertyType.Tzoffsetfrom);
        super.validateRequiredOneProperties(requireOneList);
    }
}
