package com.ljd.hackajob.phonebook.api.exceptions;

import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ljd.hackajob.phonebook.model.ExceptionResponse;

/**
 * 
 * @author leodavison
 *
 */
public class ExceptionResponseFactory {
    private ExceptionResponseFactory() {
        // not meant to be instantiated
    }
    
    private static String getMessageFormat(String bundleName, String messageKey) {
        return ResourceBundle.getBundle(bundleName).getString(messageKey);
    }
    public static Response buildResponse(int httpStatusCode, String messageBundle, String messageKey, Object...inserts) {
        ExceptionResponse er = new ExceptionResponse(messageKey, String.format(getMessageFormat(messageBundle, messageKey), inserts));
        return Response.status(httpStatusCode).entity(er).type(MediaType.APPLICATION_JSON).build();
    }
}
