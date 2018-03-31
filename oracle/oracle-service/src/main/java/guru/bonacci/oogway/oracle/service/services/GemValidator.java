package guru.bonacci.oogway.oracle.service.services;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import guru.bonacci.oogway.shareddomain.GemCarrier;

public class GemValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return GemCarrier.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		GemCarrier carrier = (GemCarrier) target;
		if ("I cannot be inserted".equalsIgnoreCase(carrier.getSaying())) {
            errors.reject("bla.bla.bla");
	    }
	}
}