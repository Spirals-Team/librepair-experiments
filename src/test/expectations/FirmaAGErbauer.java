package com.github.funthomas424242.domain;
import javax.annotation.Generated;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

@Generated(value="com.github.funthomas424242.rades.annotations.processors.RadesBuilderProcessor"
        , date="2018-04-06T20:36:46.750"
        , comments="com.github.funthomas424242.domain.Firma")
public class FirmaAGErbauer {

    private Firma firma;

    public FirmaAGErbauer(){
        this(new Firma());
    }

    public FirmaAGErbauer( final Firma firma ){
        this.firma = firma;
    }

    public Firma build() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final java.util.Set<ConstraintViolation<Firma>> constraintViolations = validator.validate(this.firma);

        if (constraintViolations.size() > 0) {
            java.util.Set<String> violationMessages = new java.util.HashSet<String>();

            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                violationMessages.add(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
            }

            throw new ValidationException("Firma is not valid:\n" + StringUtils.join(violationMessages, "\n"));
        }
        final Firma value = this.firma;
        this.firma = null;
        return value;
    }

    public FirmaAGErbauer withBetriebeNr( final java.lang.String betriebeNr ) {
        this.firma.betriebeNr = betriebeNr;
        return this;
    }

    public FirmaAGErbauer withName( final java.lang.String name ) {
        this.firma.name = name;
        return this;
    }

    public FirmaAGErbauer withGruendungstag( final java.util.Date gruendungstag ) {
        this.firma.gruendungstag = gruendungstag;
        return this;
    }

}