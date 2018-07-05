package com.ljd.hackajob.phonebook.api.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ljd.hackajob.phonebook.model.exceptions.Messages;

public class LastDitchExceptionMapper<T extends Throwable> implements javax.ws.rs.ext.ExceptionMapper<T> {
    private static final String CLASS = LastDitchExceptionMapper.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS);
    
    @Context
    HttpServletRequest context;

    @Override
    public Response toResponse(T exception) {
        final String METHOD = "toResponse";

        String requestURI = context.getRequestURI();
        String requestMethod = context.getMethod();
        LOG.logp(Level.SEVERE, CLASS, METHOD, "Exception thrown during {0} : {1}: {2}", new Object[] { requestMethod, requestURI, exception.getMessage() });

        try {
            return ExceptionResponseFactory.buildResponse(
                    Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    Messages.BUNDLE_NAME,
                    Messages.PBE0000,
                    (Object[])null);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
