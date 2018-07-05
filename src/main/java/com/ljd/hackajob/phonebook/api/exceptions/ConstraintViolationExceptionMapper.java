package com.ljd.hackajob.phonebook.api.exceptions;

import java.util.Arrays;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.ljd.hackajob.phonebook.model.exceptions.Messages;


@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    HttpServletRequest context;

    @Override
    public Response toResponse(ConstraintViolationException ex) {        
        // Build up the list of message inserts
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String[] violationsInserts = new String[violations.size()];
        int index = 0;
        
        for (ConstraintViolation<?> violation : violations) {
            violationsInserts[index] = violation.getMessage();
            index++;
        }

        return ExceptionResponseFactory.buildResponse(
                Status.BAD_REQUEST.getStatusCode(),
                Messages.BUNDLE_NAME,
                Messages.PBE0004,
                Arrays.toString(violationsInserts));
    }
}
