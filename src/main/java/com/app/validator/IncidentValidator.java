package com.app.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entities.Incident;
import com.app.utils.LatLng;
@Component
public class IncidentValidator implements Validator {
	
    
    @Override
    public boolean supports(Class<?> aClass) {
        return Incident.class.equals(aClass);
    }
    
    @Override
    public void validate(Object target, Errors errors){
        Incident incident= (Incident) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "incidentName", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "locationString", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tags", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "topic", "error.empty");
        
        parseAditionalProperties(incident,errors);
		parseLocation(incident,errors);

       
    }
    
	private void parseLocation(Incident incident, Errors errors) {

		if (incident.getLocationString() != null && !incident.getLocationString().isEmpty() && incident.getLocationString().contains(",")) {
			String[] location = incident.getLocationString().trim().split(",");
			incident.setLocation(new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])));
		}
		else {
			errors.rejectValue("locationString", "error.location");
		}

	}

	private void parseAditionalProperties(Incident incident, Errors errors) {
		if (incident.getAditionalPropertiesString() != null && !incident.getAditionalPropertiesString().isEmpty()) {
			if( incident.getAditionalPropertiesString().contains(",")) {
				String[] aditionalProperties = incident.getAditionalPropertiesString().trim().split(",");
				Map<String, String> result = new HashMap<String, String>();
				for (int i = 0; i < aditionalProperties.length; i++) {
					if(aditionalProperties[i].contains("/")) {
						String[] pv = aditionalProperties[i].split("/");
						result.put(pv[0], pv[1]);
					}
					else {
						errors.rejectValue("aditionalPropertiesString", "error.aditionalProperties");
					}

					
				}
				incident.setAditionalProperties(result);
			}
			else {
				errors.rejectValue("aditionalPropertiesString", "error.aditionalProperties");
			}
			
		} else {
			incident.setAditionalProperties(new HashMap<String, String>());

		}

	}

}
