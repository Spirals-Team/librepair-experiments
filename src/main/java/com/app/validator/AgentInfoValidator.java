package com.app.validator;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entities.Agent;
import com.app.services.AgentInfoService;

@Component
public class AgentInfoValidator implements Validator {

	@Autowired
	private AgentInfoService agentInfoService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Agent.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Agent agentInfo = (Agent) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.empty");
		if (agentInfo.getKind() == 0)
			errors.rejectValue("kind", "error.kind");

		if (!agentInfo.getId().equals("") || !agentInfo.getPassword().equals("") || agentInfo.getKind() != 0) {
			Agent posibleAgent = agentInfoService.findById(agentInfo);
			if (posibleAgent == null) {
				errors.rejectValue("id", "error.user.identification");
			} else if (!posibleAgent.getPassword().equals(agentInfo.getPassword())) {
				errors.rejectValue("password", "error.password.identification");
			} else if (posibleAgent.getKind() != agentInfo.getKind()) {
				errors.rejectValue("kind", "error.kind");
			}
		}
	}
}
