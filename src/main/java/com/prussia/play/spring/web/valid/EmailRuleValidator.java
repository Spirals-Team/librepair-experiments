package com.prussia.play.spring.web.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailRuleValidator implements ConstraintValidator<EmailRule, String> {

	@Override
	public void initialize(EmailRule arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		// TODO Auto-generated method stub
		return false;
	}


}
