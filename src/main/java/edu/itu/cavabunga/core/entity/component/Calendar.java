package edu.itu.cavabunga.core.entity.component;

import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.property.Prodid;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.entity.property.Version;
import edu.itu.cavabunga.exception.Validation;
import org.apache.commons.lang.Validate;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Calendar extends Component {
    @Override
    public void validate(){
        if(this.getParent() != null){
            throw new Validation("Calendar component cannot have parent component");
        }

        super.validate();

        List<PropertyType> optionalOneList = new ArrayList<PropertyType>();
        optionalOneList.add(PropertyType.Calscale);
        optionalOneList.add(PropertyType.Method);
        super.validateOptionalOneProperties(optionalOneList);

        List<PropertyType> requiredOneList = new ArrayList<PropertyType>();
        requiredOneList.add(PropertyType.Prodid);
        requiredOneList.add(PropertyType.Version);
        super.validateRequiredOneProperties(requiredOneList);
    }
}
