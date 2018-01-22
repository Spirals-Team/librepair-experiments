package py.org.fundacionparaguaya.pspserver.surveys.validation;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.Property;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Property.TypeEnum;

import static py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationResult.invalid;
import static py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationResult.valid;


/**
 * Created by rodrigovillalba on 10/24/17.
 */
public interface PropertyValidator extends PropertyValidatorFunction<Property, String, Object, ValidationResult> {


    static PropertyValidator validType() {
        return (Property property, String propertyName, Object propertyValue) -> {
            ValidationResult response;
            if (property.valueIsOfValidType(propertyValue)) {
                response = valid();
            } else  {
                //If the property is not valid because is an array
                if(TypeEnum.ARRAY.equals(property.getType())) {
                    //Verify if contains items and if it a json, we compare if a value  is included in the json
                    if(property.getItems()!=null && property.getItems().validateContent(propertyValue)) {
                        response = valid();
                    }else {
                        response = invalid(propertyName, "Property '" + propertyName + "' is of invalid type. Required: '" + property.getType() + "'");
                    }
                } else {
                    response = invalid(propertyName, "Property '" + propertyName + "' is of invalid type. Required: '" + property.getType() + "'");
                }
            }
            return response;
        };
    }

}
