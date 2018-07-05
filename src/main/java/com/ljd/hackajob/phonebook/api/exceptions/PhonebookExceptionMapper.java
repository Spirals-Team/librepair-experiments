package com.ljd.hackajob.phonebook.api.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

public class PhonebookExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<PhonebookException> {

    @Override
    public Response toResponse(PhonebookException exception) {
        try {
            return ExceptionResponseFactory.buildResponse(
                    exception.getHttpStatus(),
                    exception.getMessageBundle(),
                    exception.getMessageKey(),
                    exception.getMessageInserts());
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
