package up.god.micro.method;

import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.*;

@EndPoint
@Path("/api")
public class MethodUsage {

    @GET
    @Path("/method")
    public String sayGet() {
        return "Hi, HttpMethod = GET";
    }

    @POST
    @Path("/method")
    public String sayPost() {
        return "Hi, HttpMethod = POST";
    }

    @DELETE
    @Path("/method")
    public String sayDelete() {
        return "Hi, HttpMethod = DELETE";
    }

    @PUT
    @Path("/method")
    public String sayPut() {
        return "Hi, HttpMethod = PUT";
    }

    @HEAD
    @Path("/method")
    public String sayHead() {
        return "Hi, HttpMethod = HEAD";
    }

    @OPTIONS
    @Path("/method")
    public String sayOptions() {
        return "Hi, HttpMethod = OPTIONS";
    }

    @PATCH
    @Path("/method")
    public String sayPatch() {
        return "Hi, HttpMethod = PATCH";
    }
}
