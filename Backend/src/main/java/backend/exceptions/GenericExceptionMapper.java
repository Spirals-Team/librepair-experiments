package backend.exceptions;

import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    static private final Gson gson = new Gson();

    @Override
    public Response toResponse(Throwable ex) {

        Response.StatusType type = getStatusType(ex);
        Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);

        ErrorMessage error = new ErrorMessage(
                type.getStatusCode(),
                type.getReasonPhrase(),
                ex.getLocalizedMessage());

        String errJson = gson.toJson(error);
        return Response.status(error.getStatusCode())
                .entity(errJson)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Response.StatusType getStatusType(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            return ((WebApplicationException) ex).getResponse().getStatusInfo();
        }
        if (ex instanceof AuthenticationException) {
            return Response.Status.FORBIDDEN;
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }

    //Small hack, to provide json-error response in the filter
    public static Response makeErrRes(String msg, int status) {
        ErrorMessage error = new ErrorMessage(
                status,
                msg,
                msg);

        String errJson = gson.toJson(error);
        return Response.status(error.getStatusCode())
                .entity(errJson)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
