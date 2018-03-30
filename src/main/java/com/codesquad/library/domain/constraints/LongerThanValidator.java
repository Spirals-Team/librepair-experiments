package com.codesquad.library.domain.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongerThanValidator implements ConstraintValidator<LongerThan, String> {

    private int requiredLength;

    @Override
    public void initialize(LongerThan constraintAnnotation) {
        requiredLength = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= requiredLength;
    }
}
