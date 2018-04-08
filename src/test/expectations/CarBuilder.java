package com.github.funthomas424242.domain;
import javax.annotation.Generated;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

@Generated(value="com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor"
        , date="2018-04-06T20:36:46.750"
        , comments="com.github.funthomas424242.domain.Auto")
public class CarBuilder {

    private Auto auto;

    public CarBuilder(){
        this(new Auto());
    }

    public CarBuilder( final Auto auto ){
        this.auto = auto;
    }

    public Auto build() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final java.util.Set<ConstraintViolation<Auto>> constraintViolations = validator.validate(this.auto);

        if (constraintViolations.size() > 0) {
            java.util.Set<String> violationMessages = new java.util.HashSet<String>();

            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                violationMessages.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
            }

            throw new ValidationException("Auto is not valid:\n" + StringUtils.join(violationMessages, "\n"));
        }
        final Auto value = this.auto;
        this.auto = null;
        return value;
    }

    public CarBuilder withMotor( final java.lang.Object motor ) {
        this.auto.motor = motor;
        return this;
    }

    public CarBuilder withTyp( final java.lang.String typ ) {
        this.auto.typ = typ;
        return this;
    }

    public CarBuilder withHersteller( final java.lang.String hersteller ) {
        this.auto.hersteller = hersteller;
        return this;
    }

}
