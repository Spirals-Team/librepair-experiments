package com.les.brouilles.planner.service.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DevisJsonConstraintValidator.class)
@Documented
public @interface DevisJsonValide {
	String message() default "JSON de devis invalide";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}