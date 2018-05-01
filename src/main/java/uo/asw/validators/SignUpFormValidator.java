package uo.asw.validators;

import uo.asw.dbManagement.model.Operator;
import uo.asw.dbManagement.services.OperatorsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class SignUpFormValidator implements Validator {
	
	@Autowired
	private OperatorsService operatorsService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Operator.class.equals(aClass);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Operator operator= (Operator) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identifier", "Error.empty");
		
		Operator operatorEnBD=operatorsService.getUserByIdentifier(operator.getIdentifier());
		
		if (operatorEnBD != null) {
			errors.rejectValue("identifier", "Error.signup.identifier.duplicate");
		}
		if (operator.getIdentifier().length() < 5 || operator.getIdentifier().length() > 24) {
			errors.rejectValue("identifier", "Error.signup.identifier.length");
		}
		if (operator.getName().length()< 1 || operator.getName().length()>24) {
			errors.rejectValue("name", "Error.signup.name.length");
		}
		if (operator.getPassword().length() < 5 || operator.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
		}
		if (!operator.getPasswordConfirm().equals(operator.getPassword())) {
			errors.rejectValue("passwordConfirm",
					"Error.signup.passwordConfirm.coincidence");
		}
	}
}

