package up.god.micro.params;

import io.vertx.up.annotations.EndPoint;
import io.vertx.up.annotations.SessionData;

import javax.ws.rs.*;

@EndPoint
@Path("/api")
public class SessionParamExecutor {

    @POST
    @Path("param/session/{id}")
    @SessionData("user")
    public String saveSession(
            @PathParam("id") final String id) {
        System.out.println(id);
        return id;
    }

    @GET
    @Path("param/session")
    public String saySession(
            @SessionParam("user") final String user
    ) {
        System.out.println(user);
        return user;
    }
}
