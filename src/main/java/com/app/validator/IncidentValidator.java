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
        
        parseAditionalProperties(incident);
		parseLocation(incident);

       
    }
    
    private void parseLocation(Incident incident) {

		if (incident.getLocationString() != null && !incident.getLocationString().isEmpty()) {
			String[] location = incident.getLocationString().trim().split(",");
			incident.setLocation(new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])));
		}

	}

	private void parseAditionalProperties(Incident incident) {
		if (incident.getAditionalPropertiesString() != null && !incident.getAditionalProperties().isEmpty()) {
			String[] aditionalProperties = incident.getAditionalPropertiesString().trim().split(",");
			Map<String, String> result = new HashMap<String, String>();
			for (int i = 0; i < aditionalProperties.length; i++) {
				String[] pv = aditionalProperties[i].split("/");
				result.put(pv[0], pv[1]);
			}
			incident.setAditionalProperties(result);
		} else {
			incident.setAditionalProperties(new HashMap<String, String>());

		}

	}

}
