package httpErrors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletContext;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.InvalidObjectException;

@Provider
public class InvalidInputExceptionMapper extends Exception implements ExceptionMapper<IllegalArgumentException> {


    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public InvalidInputExceptionMapper() {
        super("TEST EXCEPTION HANDLER");
    }

    public InvalidInputExceptionMapper(String err) {
        super(err);
    }


    @Context
    ServletContext context;

    @Override
    public Response toResponse(IllegalArgumentException ex) {
        JsonObject error = new JsonObject();
        JsonObject errorDetail = new JsonObject();
        int statusCode = 400;//ex.getResponse().getStatus(); /**todo check this.../
        errorDetail.addProperty("code", statusCode);
        errorDetail.addProperty("message", "The requested resource was not found on our server");
        error.add("error", errorDetail);
        return Response.status(statusCode).entity(gson.toJson(error)).type(MediaType.APPLICATION_JSON).build();
    }



}
