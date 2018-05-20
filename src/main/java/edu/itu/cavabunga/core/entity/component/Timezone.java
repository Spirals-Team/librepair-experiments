package edu.itu.cavabunga.core.entity.component;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.entity.property.Tzid;
import edu.itu.cavabunga.exception.Validation;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Timezone extends Component {
    @Override
    public void validate(){
        if(!(this.getParent() instanceof Calendar)){
            throw new Validation("Timezone component cannot be child of:" + this.getParent().getClass().getName());
        }

        super.validate();

        List<PropertyType> optionalOneList = new ArrayList<PropertyType>();
        optionalOneList.add(PropertyType.Lastmod);
        optionalOneList.add(PropertyType.Tzurl);
        super.validateOptionalOneProperties(optionalOneList);

        List<PropertyType> requiredOneList = new ArrayList<PropertyType>();
        requiredOneList.add(PropertyType.Tzid);
        super.validateRequiredOneProperties(requiredOneList);
    }
}
