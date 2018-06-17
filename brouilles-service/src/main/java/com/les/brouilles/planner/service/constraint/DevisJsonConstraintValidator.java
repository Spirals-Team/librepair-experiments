package com.les.brouilles.planner.service.constraint;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.les.brouilles.planner.service.dto.DevisDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DevisJsonConstraintValidator implements ConstraintValidator<DevisJsonValide, DevisDTO> {

	private static final String MESSAGE = "Le devis {0} avec pour type {1} a le json suivant invalide : {2}";

	private DevisJsonContentValidator devisJsonValidator;

	@Autowired
	public DevisJsonConstraintValidator(final DevisJsonContentValidator jsonValidator) {
		this.devisJsonValidator = jsonValidator;
	}

	@Override
	public void initialize(final DevisJsonValide constraintAnnotation) {

	}

	@Override
	public boolean isValid(final DevisDTO devis, final ConstraintValidatorContext context) {

		overrideMessage(devis, context);

		return devisJsonValidator.validate(devis);
	}

	private void overrideMessage(final DevisDTO devis, final ConstraintValidatorContext context) {

		final String message = MessageFormat.format(MESSAGE, devis.getNumeroDevis(), devis.getTypeDevis(),
				devis.getJson());

		// disable existing violation message
		context.disableDefaultConstraintViolation();
		// build new violation message and add it
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
	}
}