package io.swagger.oas.inflector.validators;


import io.swagger.oas.models.parameters.Parameter;

import java.util.Iterator;

public interface Validator {
    void validate(Object o, Parameter parameter, Iterator<Validator> next) throws ValidationException;
}